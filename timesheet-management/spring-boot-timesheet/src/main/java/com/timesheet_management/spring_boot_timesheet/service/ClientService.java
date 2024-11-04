package com.timesheet_management.spring_boot_timesheet.service;

import com.timesheet_management.spring_boot_timesheet.model.Client;
import com.timesheet_management.spring_boot_timesheet.repository.ClientRepository;
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
