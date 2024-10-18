package com.interactive_quiz_game.spring_boot_quiz.service;

import com.interactive_quiz_game.spring_boot_quiz.model.UserResponse;
import com.interactive_quiz_game.spring_boot_quiz.repository.QuizRepository;
import com.interactive_quiz_game.spring_boot_quiz.repository.UserResponseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public void insertScore(int quizId) {
        // TODO: Implement getting the quiz row with the given quizId and dynamically updating the score within the table
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
}
