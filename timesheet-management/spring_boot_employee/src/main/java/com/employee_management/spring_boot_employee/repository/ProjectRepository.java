package com.employee_management.spring_boot_employee.repository;

import com.employee_management.spring_boot_employee.model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {
    @Query(value = "SELECT CURRENT_DATE", nativeQuery = true)
    Object[] getCurrentDate();
}
