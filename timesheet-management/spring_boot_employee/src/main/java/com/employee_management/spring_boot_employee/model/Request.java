package com.employee_management.spring_boot_employee.model;

import com.employee_management.spring_boot_employee.type.Status;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Request")
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestId")
    private int requestId;

    @Column(name = "request")
    private String request;

    @Column(name = "userId")
    private int userId;

    @Column(name = "projectId")
    private int projectId;

    @Column(name = "status")
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
