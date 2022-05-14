package com.hugaomarques;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class HTMLGenerator {

  private static final String HEAD = "<head>" +
      "<meta charset=\"utf-8\">" +
      "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">" +
      "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css\" " +
      "+ \"integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">" +
      "</head>";

  private static final String HTML_BODY_TEMPLATE = "<body><h1>Top 250 movies</h1>" +
      "%s" +
      "</body>";

  private static final String MOVIE_TEMPLATE =
      "<div class=\"card text-white bg-dark mb-3\" style=\"max-width: 18rem;\">" +
        "<h4 class=\"card-header\">%s</h4>" +
        "<div class=\"card-body\">" +
          "<img class=\"card-img\" src=\"%s\" alt=\"%s\">" +
          "<p class=\"card-text mt-2\">Nota: %s - Ano: %s</p>" +
        "</div>" +
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
    return String.format(MOVIE_TEMPLATE, movie.title(), movie.urlImage(), movie.title(), movie.rating(), movie.year());
  }

}
