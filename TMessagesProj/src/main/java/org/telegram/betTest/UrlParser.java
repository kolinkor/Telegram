package org.telegram.betTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class UrlParser {

    public static Map<String, String> getParametersFromURL(String urlString) {
        Map<String, String> queryPairs = new HashMap<>();
        try {
            URL url = new URL(urlString);
            String[] pairs = url.getRef().split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                queryPairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryPairs;
    }

    public static Map<String, String> parseQueryString(String queryString) throws UnsupportedEncodingException {
        Map<String, String> queryPairs = new HashMap<>();
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return queryPairs;
    }

    public static long GetUserIdFromUrl(String urlString){

        Map<String, String> params = getParametersFromURL(urlString);
        String tgWebAppData = params.get("tgWebAppData");

        if (tgWebAppData != null) {
            try {
                Map<String, String> tgWebAppDataParams = parseQueryString(tgWebAppData);
                String userJson = tgWebAppDataParams.get("user");

                if (userJson != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode userNode = mapper.readTree(URLDecoder.decode(userJson, "UTF-8"));
                    long userId = userNode.get("id").asLong();
                    System.out.println("User ID: " + userId);
                    return userId;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public static String GetTgWebAppData(String urlString){
        Map<String, String> params = getParametersFromURL(urlString);
        return params.get("tgWebAppData");
    }
}
