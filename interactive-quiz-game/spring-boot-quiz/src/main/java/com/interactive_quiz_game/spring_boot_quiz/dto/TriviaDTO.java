package com.interactive_quiz_game.spring_boot_quiz.dto;

import lombok.Data;

@Data
public class TriviaDTO {
    // TODO: Implement the POST request from React frontend when user inputs username,
    //  selects which category, and selects which difficulty (use fetch)
    private String username;
    private String categories;
    private String difficulties;
}
