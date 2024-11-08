package com.employee_management.spring_boot_employee.service;

import com.employee_management.spring_boot_employee.model.Request;
import com.employee_management.spring_boot_employee.repository.RequestRepository;
import com.employee_management.spring_boot_employee.type.Status;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestService {

    private RequestRepository requestRepository;

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public boolean addNewRequest(
            String request,
            int userId,
            int projectId) {
        Request req = new Request();
        if (!requestRepository.isAdminOrManager(userId)) {
            System.out.println("User trying to make request is not Admin or Manager");
            return false;
        }

        req.setRequest(request);
        req.setUserId(userId);
        req.setProjectId(projectId);
        req.setStatus(Status.Submitted);

        requestRepository.save(req);
        return true;
    }

    public void setNewStatus(int requestId, String status) {
        Optional<Request> foundRequest = requestRepository.findById(requestId);
        if (foundRequest.isPresent()) {
            Request req = foundRequest.get();
            req.setStatus(Status.valueOf(status));

            requestRepository.save(req);
        }
    }
}
