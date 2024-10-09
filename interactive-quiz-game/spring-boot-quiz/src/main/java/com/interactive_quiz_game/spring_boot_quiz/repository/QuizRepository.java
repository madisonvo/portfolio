package com.interactive_quiz_game.spring_boot_quiz.repository;

import com.interactive_quiz_game.spring_boot_quiz.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
