package com.employee_management.spring_boot_employee.repository;

import com.employee_management.spring_boot_employee.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
}
