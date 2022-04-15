package com.hugaomarques;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Top250Imdb {

    private static final String URL = "https://imdb-api.com/en/API/Top250Movies/";

    private static final String API_TOKEN = System.getenv("IMDB_API_TOKEN");

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        if (API_TOKEN == null || API_TOKEN.isEmpty()) {
            throw new IllegalStateException("Token is empty");
        }
        final var request = HttpRequest.newBuilder()
            .uri(new URI(URL + API_TOKEN))
            .GET()
            .build();

        final var httpClient = HttpClient.newHttpClient();
        final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
