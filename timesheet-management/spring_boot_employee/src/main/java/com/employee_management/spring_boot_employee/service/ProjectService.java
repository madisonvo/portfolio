package com.employee_management.spring_boot_employee.service;

import com.employee_management.spring_boot_employee.model.Project;
import com.employee_management.spring_boot_employee.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void addNewProject(
            String projectName,
            int clientId,
            Date startDate,
            Date endDate) {
        Project project = new Project();
        project.setProjectName(projectName);
        project.setClientId(clientId);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setActive(isActive(startDate, endDate) ? true : false);

        projectRepository.save(project);
    }

    private boolean isActive(Date startDate, Date endDate) {
        Date currentDate = (Date) projectRepository.getCurrentDate()[0];
        if (currentDate.after(startDate) && currentDate.before(endDate)) {
            return true;
        }

        return false;
    }
}
