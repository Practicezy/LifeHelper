package com.example.r.lifehelper.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {

    /*根据给定的url获得比特流*/
    @Nullable
    public static byte[] getByteArrayFromUrl(String urlSpec) {
        HttpURLConnection connection = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            URL url = new URL(urlSpec);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();

            int len = 0;
            byte[] buffer = new byte[8 * 1024];

            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, len);
            }
            is.close();
            bos.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return bos.toByteArray();
    }

    /*根据url解析出字符串*/
    @NonNull
    public static String getUrlString(String urlSpec) {
        return new String(getByteArrayFromUrl(urlSpec));
    }
}
