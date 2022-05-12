package com.hugaomarques;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
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
        final var movies = parseContentJson(response.body());
        final var ids = new ArrayList<String>();
        final var ranks = new ArrayList<String>();
        final var titles = new ArrayList<String>();
        final var fullTitles = new ArrayList<String>();
        final var years = new ArrayList<String>();
        final var images = new ArrayList<String>();
        final var crews = new ArrayList<List<String>>();
        final var imdbRatingsCount = new ArrayList<String>();
        movies.stream().forEach(
            movie -> {
                ids.add(parseId(movie));
                ranks.add(parseRank(movie));
                titles.add(parseTitle(movie));
                fullTitles.add(parseFullTitle(movie));
                years.add(parseYears(movie));
                images.add(parseImages(movie));
                crews.add(parseCrew(movie));
                imdbRatingsCount.add(parseImdbRatingCount(movie));
            }
        );

        System.out.println(movies);
        System.out.println(ids);
        System.out.println(ranks);
        System.out.println(titles);
        System.out.println(fullTitles);
        System.out.println(years);
        System.out.println(images);
        System.out.println(crews);
        System.out.println(imdbRatingsCount);

    }

    private static String parseImdbRatingCount(String movie) {
        return parseAttribute("imDbRatingCount", movie);
    }

    private static List<String> parseCrew(String movie) {
        return Arrays.asList(parseAttribute("crew", movie).split(","));
    }

    private static String parseImages(String movie) {
        return parseAttribute("image", movie);
    }

    private static String parseYears(String movie) {
        return parseAttribute("year", movie);
    }

    private static String parseFullTitle(String movie) {
        return parseAttribute("fullTitle", movie);
    }

    private static String parseTitle(String movie) {
        return parseAttribute("title", movie);
    }

    private static String parseRank(String movie) {
        return parseAttribute("rank", movie);
    }

    private static String parseId(String movie) {
        return parseAttribute("id", movie);
    }

    private static String parseAttribute(String attribute, String movie) {
        var matcher = Pattern.compile("\""+attribute+"\":\"(.*?)\"").matcher(movie);
        matcher.find();
        return matcher.group(1);
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
