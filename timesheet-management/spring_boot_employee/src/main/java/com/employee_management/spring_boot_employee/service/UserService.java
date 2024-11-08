package com.employee_management.spring_boot_employee.service;

import com.employee_management.spring_boot_employee.model.User;
import com.employee_management.spring_boot_employee.repository.UserRepository;
import com.employee_management.spring_boot_employee.type.UserType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
        if (!email.endsWith("@nucleusteq.com")) {
            System.out.println("Not a valid organization email");
            return false;
        }

        if (!userRepository.userExists(email)) {
            User user = new User();
            user.setUserType(UserType.valueOf(userType));
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

        System.out.println("User with given email already exists");
        return false;
    }

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

    @Transactional
    public void addSkills(int userId, String skills) {
        String[] skillsArray = skills.split(",");
        for (String skill : skillsArray) {
            userRepository.addSkills(userId, skill);
        }
    }
}
