package com.hugaomarques;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Top250Imdb {

    private static final String API_TOKEN = System.getenv("IMDB_API_TOKEN");

    public static void main(String[] args) throws IOException {
        if (API_TOKEN == null || API_TOKEN.isEmpty()) {
            throw new IllegalStateException("Token is empty");
        }
        final var responseContent = new ImdbAPIClient(API_TOKEN).getBody();
        final var movies = new ImdbJsonParser(responseContent).parse();

        try (var printWriter = new PrintWriter(new FileWriter("movies.html"))) {
            final var htmlGenerator = new HTMLGenerator(printWriter);
            htmlGenerator.generate(movies);
        }
    }
}
