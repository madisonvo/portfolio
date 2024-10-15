package com.interactive_quiz_game.spring_boot_quiz.controller;

import com.interactive_quiz_game.spring_boot_quiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class RestSpringBootController {

//    @RequestMapping("/hello")
//    public String hello() {
//        return "Hello world";
//    }
//
//    @GetMapping(value = "/callclienthello")
//    private String getHelloClient() {
//        String uri = "http://localhost:8080/hello";
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(uri, String.class);
//        return result;
//    }
//
//    @GetMapping(value = "/questions")
//    public List<Object> getQuestions() {
//        String url = "https://the-trivia-api.com/v2/questions";
//        RestTemplate restTemplate = new RestTemplate();
//        Object[] questions = restTemplate.getForObject(url, Object[].class);
//        return Arrays.asList(questions);
//    }
    @Autowired
    private UserService userService;

    @GetMapping(value = "/questions")
    public List<Map<String, Object>> getTrivia(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "difficulty", required = false) String difficulty) {
        return userService.getQuestions(category, difficulty);
    }
}
