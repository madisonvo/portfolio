package com.interactive_quiz_game.spring_boot_quiz.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interactive_quiz_game.spring_boot_quiz.dto.QuestionDTO;
import com.interactive_quiz_game.spring_boot_quiz.model.Option;
import com.interactive_quiz_game.spring_boot_quiz.model.Question;
import com.interactive_quiz_game.spring_boot_quiz.model.Quiz;
import com.interactive_quiz_game.spring_boot_quiz.model.User;
import com.interactive_quiz_game.spring_boot_quiz.repository.OptionRepository;
import com.interactive_quiz_game.spring_boot_quiz.repository.QuestionRepository;
import com.interactive_quiz_game.spring_boot_quiz.repository.QuizRepository;
import com.interactive_quiz_game.spring_boot_quiz.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;
    private UserRepository userRepository;
    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;
    private OptionRepository optionRepository;

    public QuestionService(
            UserRepository userRepository,
            QuizRepository quizRepository,
            QuestionRepository questionRepository,
            OptionRepository optionRepository) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.optionRepository = optionRepository;
    }

    public List<QuestionDTO> getQuestions(String categories, String difficulties) {
        String url = "https://the-trivia-api.com/v2/questions";
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("categories", categories)
                .queryParam("difficulties", difficulties)
                .build()
                .toUri();

        String response = restTemplate.getForObject(uri, String.class);
        try {
            return objectMapper.readValue(response, objectMapper.getTypeFactory().constructCollectionType(List.class, QuestionDTO.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int insertUser(String username) {
        User user = new User();
        user.setUsername(username);

        userRepository.save(user);
        return user.getUserId();
    }

    public int insertQuiz(String categories, String difficulties, int userId) {
        Quiz quiz = new Quiz();
        quiz.setUserId(userId);
        quiz.setCategory(categories);
        quiz.setDifficulty(difficulties);
        quiz.setScore(0);

        quizRepository.save(quiz);
        return quiz.getQuizId();
    }

    public void insertQuestions(List<QuestionDTO> questionDTOs, int quizId) {
        List<Question> questions = questionDTOs.stream()
                .map(dto -> {
                    Question question = new Question();
                    question.setQuizId(quizId); // TODO: implement logic for getting quizId
                    question.setDifficulty(dto.getDifficulty());
                    question.setQuestion(dto.getQuestion().getText());
                    return question;
                })
                .collect(Collectors.toList());

        questionRepository.saveAll(questions);

        for (int i = 0; i < questions.size(); i++) {
            QuestionDTO dto = questionDTOs.get(i);
            Question savedQuestion = questions.get(i);
            insertOptions(savedQuestion.getQuestionId(), dto.getCorrectAnswer(), dto.getIncorrectAnswers());
        }
    }

    public void insertOptions(int questionId, String correctAnswer, List<String> incorrectAnswers) {
        List<Option> options = new ArrayList<>();

        Option correctOption = new Option();
        correctOption.setQuestionId(questionId);
        correctOption.setOption(correctAnswer);
        correctOption.setCorrect(true);
        options.add(correctOption);

        for (String incorrectAnswer : incorrectAnswers) {
            Option incorrectOption = new Option();
            incorrectOption.setQuestionId(questionId);
            incorrectOption.setOption(incorrectAnswer);
            incorrectOption.setCorrect(false);
            options.add(incorrectOption);
        }

        optionRepository.saveAll(options);
    }
}
