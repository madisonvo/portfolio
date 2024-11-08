package com.employee_management.spring_boot_employee.repository;

import com.employee_management.spring_boot_employee.model.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<Request, Integer> {
    @Query(value = "SELECT userType != 'Employee' FROM \"User\" WHERE userId = :userId", nativeQuery = true)
    boolean isAdminOrManager(int userId);
}
