package com.interactive_quiz_game.spring_boot_quiz.repository;

import com.interactive_quiz_game.spring_boot_quiz.model.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserResponseRepository extends JpaRepository<UserResponse, Integer> {
}
