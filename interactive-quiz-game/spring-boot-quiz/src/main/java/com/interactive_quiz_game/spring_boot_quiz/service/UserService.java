package com.interactive_quiz_game.spring_boot_quiz.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final RestTemplate restTemplate;

    public UserService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Map<String, Object>> getQuestions(String category, String difficulty) {
        String url = "https://the-trivia-api.com/v2/questions";
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("category", category)
                .queryParam("difficulty", difficulty)
                .build()
                .toUri();

        Map<String, Object>[] response = restTemplate.getForObject(uri, Map[].class);
        return response != null ? Arrays.asList(response) : List.of();
    }
}
