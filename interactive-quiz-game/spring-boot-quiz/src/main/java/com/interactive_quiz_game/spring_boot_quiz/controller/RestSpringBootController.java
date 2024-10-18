package com.interactive_quiz_game.spring_boot_quiz.controller;

import java.util.List;

import com.interactive_quiz_game.spring_boot_quiz.service.UserResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.interactive_quiz_game.spring_boot_quiz.dto.QuestionDTO;
import com.interactive_quiz_game.spring_boot_quiz.service.SessionService;

@RestController
public class RestSpringBootController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserResponseService userResponseService;

    @PostMapping(value = "/users")
    public ResponseEntity<Integer> getUser(@RequestParam("username") String username) {
        return ResponseEntity.ok(sessionService.insertUser(username));
    }

    @PostMapping(value = "/quizzes")
    public ResponseEntity<Integer> getQuiz(
            @RequestParam("categories") String categories,
            @RequestParam("difficulties") String difficulties,
            @RequestParam("userId") int userId) {
        return ResponseEntity.ok(sessionService.insertQuiz(categories, difficulties, userId));
    }

    @GetMapping(value = "/questions")
    public ResponseEntity<List<QuestionDTO>> getTrivia(
            @RequestParam("categories") String categories,
            @RequestParam("difficulties") String difficulties,
            @RequestParam("quizId") int quizId) {
        List<QuestionDTO> questions = sessionService.getQuestions(categories, difficulties);
        sessionService.insertQuestions(questions, quizId);
        if (questions == null || questions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(questions);
    }

    @PostMapping(value = "/scores")
    public void getScore(@RequestParam("quizId") int quizId) {
        userResponseService.insertScore(quizId);
    }

    @PostMapping(value = "/responses")
    public void getUserResponse(
            @RequestParam("userId") int userId,
            @RequestParam("quizId") int quizId,
            @RequestParam("questionId") int questionId,
            @RequestParam("optionId") int optionId,
            @RequestParam("isCorrect") boolean isCorrect) {
        userResponseService.insertUserResponse(userId, quizId, questionId, optionId, isCorrect);
    }
}
