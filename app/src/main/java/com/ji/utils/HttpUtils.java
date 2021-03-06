package com.ji.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtils {
    private static final String TAG = "HttpUtils";

    public static String getString(String address) {
        LogUtils.v(TAG, "getString address:" + address);
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(address);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String s;
                while ((s = br.readLine()) != null) {
                    sb.append(s);
                }
                br.close();
            }
        } catch (IOException e) {
            LogUtils.e(TAG, "getString address:" + address, e);
        }
        return sb.toString();
    }

    public static void saveFile(String address, File file) {
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                }
                fos.flush();
                fos.close();
                is.close();
            }
        } catch (IOException e) {
            LogUtils.e(TAG, "saveFile address:" + address, e);
        }
    }
}
