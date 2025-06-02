package com.example.onaffair.online_chat.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

public class WebUtils {

    public static String downloadImageAsBase64(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);

        try (InputStream in =url.openStream()){
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int byteRead;
            while ((byteRead = in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, byteRead);
            }
            byte[] imageByte = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageByte);
        }

    }



}
