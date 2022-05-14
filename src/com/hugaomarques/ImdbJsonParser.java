package com.hugaomarques;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ImdbJsonParser implements JsonParser {

  private String responseContent;

  public ImdbJsonParser(String responseContent) {
    this.responseContent = responseContent;
  }

  @Override
  public List<Movie> parse() {
    final var moviesJson = parseContentJson(responseContent);
    return moviesJson.stream()
        .map(Movie::instanceOf)
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
}
