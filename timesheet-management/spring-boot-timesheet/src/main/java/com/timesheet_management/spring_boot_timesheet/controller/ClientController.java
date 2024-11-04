package com.timesheet_management.spring_boot_timesheet.controller;

import com.timesheet_management.spring_boot_timesheet.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @PostMapping(value = "/newClient")
    public void addNewClient(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        clientService.addNewClient(firstName, lastName);
    }
}
