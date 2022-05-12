package com.hugaomarques;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Movie {

  private String id;
  private String rank;
  private String title;
  private String fullTitle;
  private String year;
  private String image;
  private List<String> crew;
  private String imdbRatingCount;

  public static Movie instanceOf(String movieAsJson) {
      var newMovie = new Movie();
      newMovie.id = parseId(movieAsJson);
      newMovie.rank = parseRank(movieAsJson);
      newMovie.title = parseTitle(movieAsJson);
      newMovie.fullTitle = parseFullTitle(movieAsJson);
      newMovie.year = parseYears(movieAsJson);
      newMovie.image = parseImages(movieAsJson);
      newMovie.crew = parseCrew(movieAsJson);
      newMovie.imdbRatingCount = parseImdbRatingCount(movieAsJson);
    return newMovie;
  }

  public String getId() {
    return id;
  }

  public String getRank() {
    return rank;
  }

  public String getTitle() {
    return title;
  }

  public String getFullTitle() {
    return fullTitle;
  }

  public String getYear() {
    return year;
  }

  public String getImage() {
    return image;
  }

  public List<String> getCrew() {
    return crew;
  }

  public String getImdbRatingCount() {
    return imdbRatingCount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Movie movie = (Movie) o;
    return id.equals(movie.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Movie{");
    sb.append("id='").append(id).append('\'');
    sb.append(", rank='").append(rank).append('\'');
    sb.append(", title='").append(title).append('\'');
    sb.append(", fullTitle='").append(fullTitle).append('\'');
    sb.append(", year='").append(year).append('\'');
    sb.append(", image='").append(image).append('\'');
    sb.append(", crew=").append(crew);
    sb.append(", imdbRatingCount='").append(imdbRatingCount).append('\'');
    sb.append('}');
    return sb.toString();
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
  
}
