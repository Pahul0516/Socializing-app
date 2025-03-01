package org.example.lab7.Domain;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Ai_Bot {

    final private String url = "https://api.anakin.ai/v1/chatbots/1344/messages";
    final private String content_type1 = "Content-Type";
    final private String content_type2 = "application/json";
    final private String authorization = "Authorization";
    final private String key  = "Bearer APS-VpsIuOmUMmQpIXT7lYz6DknsMAob0Hud";

    private String threadID;
    private String name;

    public Ai_Bot(String name){
        this.name = name;
        String content = "{\n    " +
                "\"content\": \"Numele tau pe parcursul conversatiei va fi " + this.name + "! Te rog sa te prezinti\"," +
                "\n    " +
                "\"stream\": false\n" +
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
            String threadID = contentJson.get("threadId").getAsString();
            this.threadID = threadID;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get_thread(){
        return this.threadID;
    }
}
