package com.interactive_quiz_game.spring_boot_quiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class QuestionDTO {
    private String category;
    private String id;
    private List<String> tags;
    private String difficulty;
    private List<String> regions;
    @JsonProperty("isNiche")
    private boolean isNiche;
    private QuestionText question;
    private String correctAnswer;
    private List<String> incorrectAnswers;
    private String type;

    @Data
    public static class QuestionText {
        private String text;
    }
}
