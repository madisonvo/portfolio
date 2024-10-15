package com.interactive_quiz_game.spring_boot_quiz.util;

import com.interactive_quiz_game.spring_boot_quiz.model.*;
import com.interactive_quiz_game.spring_boot_quiz.repository.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final OptionRepository optionRepository;
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final UserResponseRepository userResponseRepository;

    public AppStartupEvent(OptionRepository optionRepository, QuestionRepository questionRepository,
                           QuizRepository quizRepository, UserRepository userRepository, UserResponseRepository userResponseRepository) {
        this.optionRepository = optionRepository;
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.userResponseRepository = userResponseRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
            Iterable<Option> options = this.optionRepository.findAll();
            options.forEach(System.out::println);

            Iterable<Question> questions = this.questionRepository.findAll();
            questions.forEach(System.out::println);

            Iterable<Quiz> quizzes = this.quizRepository.findAll();
            quizzes.forEach(System.out::println);

            Iterable<User> users = this.userRepository.findAll();
            users.forEach(System.out::println);

            Iterable<UserResponse> userResponses = this.userResponseRepository.findAll();
            userResponses.forEach(System.out::println);
    }
}
