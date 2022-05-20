package com.hugaomarques.marvel;

import java.util.regex.Pattern;

import com.hugaomarques.Content;

public class MarvelSeries implements Content, Comparable<MarvelSeries> {

  private final String title;
  private final String rating;
  private final String year;
  private final String urlImage;

  public MarvelSeries(String title, String rating, String year, String urlImage) {
    this.title = title;
    this.rating = rating;
    this.year = year;
    this.urlImage = urlImage;
  }

  public String title() {
    return title;
  }

  public String rating() {
    return rating;
  }

  public String year() {
    return year;
  }

  @Override
  public String type() {
    return "Series";
  }

  public String urlImage() {
    return urlImage;
  }

  @Override
  public int compareTo(MarvelSeries that) {
    return this.rating.compareTo(that.rating);
  }
}
