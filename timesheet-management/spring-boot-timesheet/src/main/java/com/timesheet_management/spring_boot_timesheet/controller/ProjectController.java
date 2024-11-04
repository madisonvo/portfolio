package com.timesheet_management.spring_boot_timesheet.controller;

import com.timesheet_management.spring_boot_timesheet.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping(value = "/newProject")
    public void addNewProject(
            @RequestParam("projectName") String projectName,
            @RequestParam("clientId") int clientId,
            @RequestParam("startDate") Date startDate,
            @RequestParam("endDate") Date endDate) {
        projectService.addNewProject(projectName, clientId, startDate, endDate);
    }
}
