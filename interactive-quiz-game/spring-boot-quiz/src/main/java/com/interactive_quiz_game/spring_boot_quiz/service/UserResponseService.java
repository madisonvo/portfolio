package com.interactive_quiz_game.spring_boot_quiz.service;

import com.interactive_quiz_game.spring_boot_quiz.model.Quiz;
import com.interactive_quiz_game.spring_boot_quiz.model.UserResponse;
import com.interactive_quiz_game.spring_boot_quiz.repository.QuizRepository;
import com.interactive_quiz_game.spring_boot_quiz.repository.UserResponseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserResponseService {
    private RestTemplate restTemplate;
    private QuizRepository quizRepository;
    private UserResponseRepository userResponseRepository;

    public UserResponseService(
            QuizRepository quizRepository,
            UserResponseRepository userResponseRepository) {
        this.restTemplate = new RestTemplate();
        this.quizRepository = quizRepository;
        this.userResponseRepository = userResponseRepository;
    }

    public void insertUserResponse(int userId, int quizId, int questionId, int optionId, boolean isCorrect) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userId);
        userResponse.setQuizId(quizId);
        userResponse.setQuestionId(questionId);
        userResponse.setOptionId(optionId);
        userResponse.setCorrect(isCorrect);

        userResponseRepository.save(userResponse);
    }

    public void updateScore(int quizId, int score) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            quiz.setScore(score);
            quizRepository.save(quiz);
        } else {
            throw new RuntimeException("Quiz not found with id: " + quizId);
        }
    }
}
