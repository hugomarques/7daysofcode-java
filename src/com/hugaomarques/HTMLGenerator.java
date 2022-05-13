package com.hugaomarques;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class HTMLGenerator {

  private static final String HEAD = "<head><meta charset=\"utf-8\"></head>";

  private static final String HTML_BODY_TEMPLATE = "<body><h1>Top 250 movies</h1>" +
      "%s" +
      "</body>";

  private static final String MOVIE_TEMPLATE =
      "<div><h2>%s</h2>" +
          "<img src=\"%s\"/>" +
          "<p>Nota: %s - Ano: %s</p>" +
      "</div>";

  private final Writer outputWritter;

  public HTMLGenerator(Writer outputWritter) {
    this.outputWritter = outputWritter;
  }

  public void generate(List<Movie> movies) {
    try {
      outputWritter.write(HEAD);
      final var moviesAsHtml = movies.stream().map(HTMLGenerator::fromMovieToMovieHTML).collect(
          Collectors.joining());
      outputWritter.write(String.format(HTML_BODY_TEMPLATE, moviesAsHtml));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static String fromMovieToMovieHTML(Movie movie) {
    return String.format(MOVIE_TEMPLATE, movie.getTitle(), movie.getImage(), movie.getRank(), movie.getYear());
  }

}
