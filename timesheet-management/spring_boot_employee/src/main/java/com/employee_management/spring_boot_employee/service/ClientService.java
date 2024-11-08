package com.employee_management.spring_boot_employee.service;

import com.employee_management.spring_boot_employee.model.Client;
import com.employee_management.spring_boot_employee.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void addNewClient(String firstName, String lastName) {
        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);

        clientRepository.save(client);
    }
}
