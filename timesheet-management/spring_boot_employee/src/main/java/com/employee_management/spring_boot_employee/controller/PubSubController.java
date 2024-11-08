package com.employee_management.spring_boot_employee.controller;

import com.employee_management.spring_boot_employee.service.EmployeePublisherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PubSubController {
    private final EmployeePublisherService employeePublisherService;

    public PubSubController(EmployeePublisherService employeePublisherService) {
        this.employeePublisherService = employeePublisherService;
    }

    @GetMapping(value = "/publish-message")
    public String publishMessage(@RequestParam("message") String message) {
        try {
            employeePublisherService.publishMessage(message);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }
}
