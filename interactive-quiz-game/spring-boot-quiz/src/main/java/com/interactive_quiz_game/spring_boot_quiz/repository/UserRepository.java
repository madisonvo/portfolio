package com.interactive_quiz_game.spring_boot_quiz.repository;

import com.interactive_quiz_game.spring_boot_quiz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
