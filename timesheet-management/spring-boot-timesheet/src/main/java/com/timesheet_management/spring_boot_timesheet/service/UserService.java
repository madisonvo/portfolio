package com.timesheet_management.spring_boot_timesheet.service;

import com.timesheet_management.spring_boot_timesheet.model.User;
import com.timesheet_management.spring_boot_timesheet.repository.UserRepository;
import com.timesheet_management.spring_boot_timesheet.type.UserType;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean verifyLogin(
            String email,
            String password) {
        return userRepository.verifyLogin(email, password);
    }

    public boolean addNewUser(
            String userType,
            String firstName,
            String lastName,
            String email,
            String password) {
        if (!userRepository.userExists(email)) {
            User user = new User();
//            user.setUserType(UserType.valueOf(userType));
            user.setUserType(userType);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            user.setClientId(null);
            user.setProjectId(null);
            user.setActive(false);

            userRepository.save(user);
            return true;
        }

        return false;
    }

    // PUT MAPPING
    public void assignClientAndProject(int userId, int clientId, int projectId) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            user.setClientId(clientId);
            user.setProjectId(projectId);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with userId: " + userId);
        }

        updateIsActive(userId);
    }

    // PUT MAPPING
    private void updateIsActive(int userId) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isPresent()) {
            User user = foundUser.get();
            if (userRepository.isActive(userId)) {
                user.setActive(true);
            } else {
                user.setActive(false);
            }
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with userId: " + userId);
        }
    }
}
