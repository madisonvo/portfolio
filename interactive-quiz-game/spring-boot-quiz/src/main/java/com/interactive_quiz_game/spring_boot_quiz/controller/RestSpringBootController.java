package com.interactive_quiz_game.spring_boot_quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interactive_quiz_game.spring_boot_quiz.dto.QuestionDTO;
import com.interactive_quiz_game.spring_boot_quiz.service.QuestionService;

@RestController
public class RestSpringBootController {

    @Autowired
    private QuestionService questionService;

    @PostMapping(value = "/users")
    public ResponseEntity<Integer> getUser(@RequestParam("username") String username) {
        return ResponseEntity.ok(questionService.insertUser(username));
    }

    @PostMapping(value = "/quizzes")
    public ResponseEntity<Integer> getQuiz(
            @RequestParam("categories") String categories,
            @RequestParam("difficulties") String difficulties,
            @RequestParam("userId") int userId) {
        return ResponseEntity.ok(questionService.insertQuiz(categories, difficulties, userId));
    }

    @GetMapping(value = "/questions")
    public ResponseEntity<List<QuestionDTO>> getTrivia(
            @RequestParam("categories") String categories,
            @RequestParam("difficulties") String difficulties,
            @RequestParam("quizId") int quizId) {
        List<QuestionDTO> questions = questionService.getQuestions(categories, difficulties);
        questionService.insertQuestions(questions, quizId);
        if (questions == null || questions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(questions);
    }
}
