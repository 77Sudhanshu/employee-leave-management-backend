package com.sudhanshu.employeeleavemanagementsystem.repository;

import com.sudhanshu.employeeleavemanagementsystem.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
    List<Employee> findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
        String fullName,
        String email);
        long countByRole(String role);
}