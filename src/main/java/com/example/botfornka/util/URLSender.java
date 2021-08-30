package com.example.botfornka.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class URLSender {

    private static final String USER_AGENT = "Mozilla/5.0";

    public String getRequestInfoByURL(String requiredUrl) {
        try {
            URL url = new URL(requiredUrl);
            String responseBody = getResponseBody(url);
            int statusCode = getStatusCode(doGetRequest(url));
            return formatResponse(responseBody, statusCode);
        } catch (IOException e) {
            return "Ошибка формирования тела запроса";
        }
    }

    private HttpURLConnection doGetRequest(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        return connection;
    }

    private String getResponseBody(URL url) throws IOException {
        try (InputStream is = url.openStream();
             BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));) {
            return readAll(rd);
        } catch (IOException e) {
            throw new IOException("IO exception");
        }
    }

    private int getStatusCode(HttpURLConnection connection) throws IOException {
        return connection.getResponseCode();
    }

    private String formatResponse(String body, int code){
        return "Полученный ответ: \n" + "Cтатус: " + code + ";\n" +
                "Тело ответа: \n" + body;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
