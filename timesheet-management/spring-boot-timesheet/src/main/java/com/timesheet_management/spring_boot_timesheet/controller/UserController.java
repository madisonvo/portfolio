package com.timesheet_management.spring_boot_timesheet.controller;

import com.timesheet_management.spring_boot_timesheet.service.UserService;
import com.timesheet_management.spring_boot_timesheet.type.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    // TODO: WORKS
    @PostMapping(value = "/login")
    public boolean checkLoginInfo(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        return userService.verifyLogin(email, password);
    }

    // TODO: RETURN NEWLY CREATED USERID
    @PostMapping(value = "/newUser")
    public boolean addNewUser(
            @RequestParam("userType") String userType,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        return userService.addNewUser(userType, firstName, lastName, email, password);
    }

    // TODO: SOMEHOW GET USERID FOR USER WE WANT TO UPDATE
    @PutMapping(value = "/assign")
    public void assignToUser(
            @RequestParam("userId") int userId,
            @RequestParam("clientId") int clientId,
            @RequestParam("projectId") int projectId) {
        userService.assignClientAndProject(userId, clientId, projectId);
    }
}
