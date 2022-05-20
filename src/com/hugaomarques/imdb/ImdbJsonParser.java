package com.hugaomarques.imdb;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.hugaomarques.JsonParser;

public class ImdbJsonParser implements JsonParser {

  private String responseContent;

  public ImdbJsonParser(String responseContent) {
    this.responseContent = responseContent;
  }

  @Override
  public List<Movie> parse() {
    final var moviesJson = parseContentJson(responseContent);
    return moviesJson.stream()
        .map(movieJson -> new Movie(
            parseTitle(movieJson),
            parseRating(movieJson),
            parseYears(movieJson),
            parseImages(movieJson)
        ))
        .collect(Collectors.toList());
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

  private static String parseImages(String movie) {
    return parseAttribute("image", movie);
  }

  private static String parseYears(String movie) {
    return parseAttribute("year", movie);
  }

  private static String parseTitle(String movie) {
    return parseAttribute("title", movie);
  }

  private static String parseRating(String movie) {
    return parseAttribute("imDbRating", movie);
  }

  private static String parseAttribute(String attribute, String movie) {
    var matcher = Pattern.compile("\""+attribute+"\":\"(.*?)\"").matcher(movie);
    matcher.find();
    return matcher.group(1);
  }
}
