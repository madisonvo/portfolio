package com.interactive_quiz_game.spring_boot_quiz.repository;

import com.interactive_quiz_game.spring_boot_quiz.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Integer> {
}
