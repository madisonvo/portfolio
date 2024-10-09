package com.interactive_quiz_game.spring_boot_quiz.repository;

import com.interactive_quiz_game.spring_boot_quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
