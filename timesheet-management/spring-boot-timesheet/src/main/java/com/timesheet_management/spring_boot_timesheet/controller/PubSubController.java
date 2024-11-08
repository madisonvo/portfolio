package com.timesheet_management.spring_boot_timesheet.controller;

import com.timesheet_management.spring_boot_timesheet.service.TimesheetPublisherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PubSubController {
    private final TimesheetPublisherService timesheetPublisherService;

    public PubSubController(TimesheetPublisherService timesheetPublisherService) {
        this.timesheetPublisherService = timesheetPublisherService;
    }

    @GetMapping(value = "/publish-message")
    public String publishMessage(@RequestParam("message") String message) {
        try {
            timesheetPublisherService.publishMessage(message);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed";
        }
    }
}
