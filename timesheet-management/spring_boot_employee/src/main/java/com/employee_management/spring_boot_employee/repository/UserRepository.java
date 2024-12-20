package com.employee_management.spring_boot_employee.repository;

import com.employee_management.spring_boot_employee.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value = "SELECT COUNT(*) = 1 FROM \"User\" WHERE email = :email AND \"password\" = :password", nativeQuery = true)
    boolean verifyLogin(String email, String password);

    @Query(value = "SELECT CURRENT_DATE <= p.endDate FROM \"User\" u JOIN Project p ON u.projectId = p.projectId WHERE u.userId = :userId", nativeQuery = true)
    boolean isActive(int userId);

    @Query(value = "SELECT COUNT(*) = 1 FROM \"User\" WHERE email = :email", nativeQuery = true)
    boolean userExists(String email);

    @Transactional
    @Modifying
    @Query(value ="UPDATE \"User\" SET skills = array_append(skills, :skill) WHERE userId = :userId", nativeQuery = true)
    void addSkills(int userId, String skill);
}
