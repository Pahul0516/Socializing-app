package org.example.lab7.Domain;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Ai_chat {


    static public String ask(String question, String threadID) {

        final String url = "https://api.anakin.ai/v1/chatbots/1344/messages";
        final String content_type1 = "Content-Type";
        final String content_type2 = "application/json";
        final String authorization = "Authorization";
        final String key  = "Bearer APS-VpsIuOmUMmQpIXT7lYz6DknsMAob0Hud";

        String content = "{\n    " +
                "\"content\": \"" + question + "\",\n" + // Add the actual question
                "\"stream\": false,\n" +                  // Ensure correct formatting with commas
                "\"threadId\": \"" + threadID + "\"\n" +
                "}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header(content_type1,content_type2)
                .header(authorization,key)
                .POST(HttpRequest.BodyPublishers.ofString(content))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            JsonObject contentJson = JsonParser.parseString(response.body()).getAsJsonObject();
            String text = contentJson.get("content").getAsString();
            return text;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
