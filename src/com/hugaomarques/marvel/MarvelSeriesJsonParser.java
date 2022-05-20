package com.hugaomarques.marvel;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.hugaomarques.JsonParser;

public class MarvelSeriesJsonParser implements JsonParser {

  private String jsonContent;

  public MarvelSeriesJsonParser(String jsonContent) {
    this.jsonContent = jsonContent;
  }

  @Override
  public List<MarvelSeries> parse() {
    final var contentMatcher = Pattern.compile("\"results\":\\[(.*?),\"next\":null,\"previous\":null}\\]").matcher(jsonContent);
    if (!contentMatcher.find()) {
      throw new IllegalStateException("Data on wrong format");
    }
    var content = contentMatcher.group(1);
    var seriesAsJson = content.split("\\},\\{\"id\"");
    return Arrays.stream(seriesAsJson).map(
        series -> new MarvelSeries(
            parseTitle(series),
            parseRating(series),
            parseYears(series),
            parseImages(series))
    ).collect(Collectors.toList());
  }

  private String parseRating(String series) {
    var matcher = Pattern.compile("\"rating\":\"(.*?)\"").matcher(series);
    matcher.find();
    var rating = matcher.group(1);
    if (rating.isEmpty()) {
      return "Livre";
    }
    return rating;
  }

  private static String parseImages(String series) {
    var matcher = Pattern.compile("\"thumbnail\":\\{\"path\":\"(.*?)\",\"extension\":\"(.*?)\"\\}").matcher(series);
    matcher.find();
    var urlImage = matcher.group(1) + "." + matcher.group(2);
    return urlImage;
  }

  private static String parseYears(String series) {
    var matcher = Pattern.compile("\"startYear\":(\\d*),\"endYear\":(\\d*)").matcher(series);
    matcher.find();
    var years = matcher.group(1) + " to " + matcher.group(2);
    return years;
  }

  private static String parseTitle(String series) {
    var matcher = Pattern.compile("\"title\":\"(.*?)\"").matcher(series);
    matcher.find();
    return matcher.group(1);
  }
}
