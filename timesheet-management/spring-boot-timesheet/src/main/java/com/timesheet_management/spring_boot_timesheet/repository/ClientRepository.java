package com.timesheet_management.spring_boot_timesheet.repository;

import com.timesheet_management.spring_boot_timesheet.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
}
