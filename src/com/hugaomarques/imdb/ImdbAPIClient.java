package com.hugaomarques.imdb;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.hugaomarques.APIClient;

public class ImdbAPIClient implements APIClient {

  private static final String URL = "https://imdb-api.com/en/API/Top250Movies/";

  private String apiToken;

  public ImdbAPIClient(String apiToken) {
    if (apiToken == null || apiToken.isEmpty()) {
      throw new IllegalStateException("Token is empty");
    }
    this.apiToken = apiToken;
  }

  @Override
  public String getBody() {
    try {
      var request = HttpRequest.newBuilder()
          .uri(new URI(URL + this.apiToken))
          .GET()
          .build();

    final var httpClient = HttpClient.newHttpClient();
    final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
    } catch (URISyntaxException | InterruptedException | IOException e) {
      throw new RuntimeException(e);
    }
  }
}
