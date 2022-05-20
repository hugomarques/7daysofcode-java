package com.hugaomarques;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.hugaomarques.imdb.ImdbAPIClient;
import com.hugaomarques.imdb.ImdbJsonParser;
import com.hugaomarques.marvel.MarvelAPIClient;
import com.hugaomarques.marvel.MarvelSeriesJsonParser;

public class Top250Imdb {

    private static final String IMDB_API_TOKEN = System.getenv("IMDB_API_TOKEN");
    private static final String MARVEL_PUBLIC_KEY = System.getenv("MARVEL_PUBLIC_KEY");
    private static final String MARVEL_PRIVATE_KEY = System.getenv("MARVEL_PRIVATE_KEY");

    public static void main(String[] args) throws IOException {
        final var responseContent = new ImdbAPIClient(IMDB_API_TOKEN).getBody();
        final var movies = new ImdbJsonParser(responseContent).parse();

        final var marvelContent = new MarvelAPIClient(MARVEL_PUBLIC_KEY, MARVEL_PRIVATE_KEY).getBody();
        final var marvelSeries = new MarvelSeriesJsonParser(marvelContent).parse();

        final var content = Stream.of(movies, marvelSeries)
            .flatMap(Collection::stream) // this is the same as contentList -> contentList.stream()
            .collect(Collectors.toList());

        Collections.sort(content, Comparator.comparing(Content::year));

        try (var printWriter = new PrintWriter("content.html")) {
            final var htmlGenerator = new HTMLGenerator(printWriter);
            htmlGenerator.generate(content);
        }
    }
}
