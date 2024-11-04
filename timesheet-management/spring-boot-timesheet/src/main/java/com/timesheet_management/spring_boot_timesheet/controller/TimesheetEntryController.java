package com.timesheet_management.spring_boot_timesheet.controller;

import com.timesheet_management.spring_boot_timesheet.service.TimesheetEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
public class TimesheetEntryController {
    @Autowired
    private TimesheetEntryService timesheetEntryService;

    @PostMapping(value = "/newTimesheet")
    public void addNewTimesheetEntry(
            @RequestParam("userId") int userId,
            @RequestParam("projectId") int projectId,
            @RequestParam("date") Date date,
            @RequestParam("hours") int hours) {
        timesheetEntryService.addNewTimesheetEntry(userId, projectId, date, hours);
    }
}
