package com.hugaomarques;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        final var moviesJson = parseContentJson(response.body());
        final var movies = moviesJson.stream()
            .map(Movie::instanceOf)
            .collect(Collectors.toList());
        final var printWriter = new PrintWriter(new FileWriter("movies.html"));
        final var htmlGenerator = new HTMLGenerator(printWriter);
        htmlGenerator.generate(movies);
    }

    private static List<String> parseContentJson(String json) {
        final var contentMatcher = Pattern.compile("\\[(.*?)\\]").matcher(json);
        if (!contentMatcher.find()) {
            throw new IllegalStateException("Data on wrong format");
        }
        var content = contentMatcher.group(0);
        final var moviesMatcher = Pattern.compile("\\{(.*?)\\}").matcher(content);
        var groupIndex = 0;
        return moviesMatcher.results().map(result -> result.group()).collect(Collectors.toList());
    }
}
