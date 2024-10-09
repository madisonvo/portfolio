package com.interactive_quiz_game.spring_boot_quiz.service;

import com.interactive_quiz_game.spring_boot_quiz.model.User;
import com.interactive_quiz_game.spring_boot_quiz.repository.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
