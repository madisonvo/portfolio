package com.interactive_quiz_game.spring_boot_quiz.controller;

import com.interactive_quiz_game.spring_boot_quiz.dto.QuestionDTO;
import com.interactive_quiz_game.spring_boot_quiz.dto.TriviaDTO;
import com.interactive_quiz_game.spring_boot_quiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestSpringBootController {

    @Autowired
    private QuestionService questionService;

    @PostMapping(value = "/users")
    public void getUser(@RequestParam("username") String username) {
        // TODO: Implement the POST request from React frontend when user inputs username,
        //  selects which category, and selects which difficulty (use fetch)
        int userId = questionService.insertUser(username);
    }

    @PostMapping(value = "/quizzes")
    public void getQuiz(@RequestParam("categories") String categories, @RequestParam("difficulties") String difficulties, int userId) {
        List<QuestionDTO> questionDTOs =  questionService.getQuestions(categories, difficulties);
        int quizId = questionService.insertQuiz(categories, difficulties, userId);
        questionService.insertQuestions(questionDTOs, quizId);
    }

    @GetMapping(value = "/questions")
    public void getTrivia(@RequestParam("categories") String categories, @RequestParam("difficulties") String difficulties) {
        List<QuestionDTO> questionDTOs =  questionService.getQuestions(categories, difficulties);
    }
}