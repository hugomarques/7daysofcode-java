package com.hugaomarques.imdb;

import java.util.Objects;

import com.hugaomarques.Content;

public class Movie implements Content, Comparable<Movie> {

  private String rating;
  private String title;
  private String year;
  private String urlImage;

  public Movie(String title, String rating, String year, String urlImage) {
    this.title = title;
    this.rating = rating;
    this.year = year;
    this.urlImage = urlImage;
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

  @Override
  public String type() {
    return "Movie";
  }

  public String urlImage() {
    return urlImage;
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
    return Objects.equals(title, movie.title) && Objects.equals(year, movie.year);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, year);
  }

  @Override
  public int compareTo(Movie that) {
    return this.rating.compareTo(that.rating);
  }
}
