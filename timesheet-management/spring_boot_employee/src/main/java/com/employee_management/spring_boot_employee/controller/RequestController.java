package com.employee_management.spring_boot_employee.controller;

import com.employee_management.spring_boot_employee.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {
    @Autowired
    private RequestService requestService;

    @PostMapping(value = "/newRequest")
    public boolean addNewRequest(
            @RequestParam("request") String request,
            @RequestParam("userId") int userId,
            @RequestParam("projectId") int projectId) {
        return requestService.addNewRequest(request, userId, projectId);
    }

    @PutMapping(value = "/updateStatus")
    public void setNewStatus(
            @RequestParam("requestId") int requestId,
            @RequestParam("status") String status) {
        requestService.setNewStatus(requestId, status);
    }
}
