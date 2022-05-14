package com.hugaomarques;

import java.util.Objects;
import java.util.regex.Pattern;

public class Movie implements Content {

  private String id;
  private String rating;
  private String title;
  private String year;
  private String image;

  public static Movie instanceOf(String movieAsJson) {
      var newMovie = new Movie();
      newMovie.id = parseId(movieAsJson);
      newMovie.rating = parseRank(movieAsJson);
      newMovie.title = parseTitle(movieAsJson);
      newMovie.year = parseYears(movieAsJson);
      newMovie.image = parseImages(movieAsJson);
    return newMovie;
  }

  public String id() {
    return id;
  }

  public String rating() {
    return this.rating;
  }

  public String title() {
    return this.title;
  }

  public String year() {
    return year;
  }

  public String urlImage() {
    return image;
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


  private static String parseImages(String movie) {
    return parseAttribute("image", movie);
  }

  private static String parseYears(String movie) {
    return parseAttribute("year", movie);
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
