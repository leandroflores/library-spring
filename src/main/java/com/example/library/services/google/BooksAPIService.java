package com.example.library.services.google;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class BooksAPIService {
    private static final String URL = "https://www.googleapis.com/books/v1/volumes";
    private static WebClient client = null;

    public BooksAPIService(WebClient.Builder builder) {
        client = builder.baseUrl(URL).build();
    }

    public static Mono<String> getBooks(String query) {
        return client.get().uri(
                uriBuilder -> uriBuilder
                        .queryParam("q", query)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
