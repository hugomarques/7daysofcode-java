package com.hugaomarques.marvel;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.hugaomarques.APIClient;

public class MarvelAPIClient implements APIClient {

  private String marvelPublicKey;
  private String marvelPrivateKey;

  private String URL_TEMPLATE = "https://gateway.marvel.com:443/v1/public/series?ts=%s&apikey=%s&hash=%s";

  public MarvelAPIClient(String marvelPublicKey, String marvelPrivateKey) {
    this.marvelPrivateKey = marvelPrivateKey;
    if (marvelPublicKey == null || marvelPublicKey.isEmpty()) {
      throw new IllegalStateException("marvelPublicKey is empty");
    }
    if (marvelPrivateKey == null || marvelPrivateKey.isEmpty()) {
      throw new IllegalStateException("marvelPrivateKey is empty");
    }
    this.marvelPublicKey = marvelPublicKey;
  }

  /**
   * For details about how to use Marvel API. See: https://developer.marvel.com/documentation/authorization
   * @return
   */
  @Override
  public String getBody() {
    final var currentTimeMillis = String.valueOf(System.currentTimeMillis());
    final var hash = md5Hex(currentTimeMillis+marvelPrivateKey+marvelPublicKey);
    try {
      var request = HttpRequest.newBuilder()
          .uri(new URI(String.format(URL_TEMPLATE, currentTimeMillis, this.marvelPublicKey, hash)))
          .GET()
          .build();
    final var httpClient = HttpClient.newHttpClient();
    final var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
    } catch (URISyntaxException | IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private String md5Hex(String value) {
    try {
      final var messageDigestMD5 = MessageDigest.getInstance("MD5");
      final var digest = new BigInteger(1, messageDigestMD5.digest(value.getBytes(StandardCharsets.UTF_8)));
      return digest.toString(16);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }
}
