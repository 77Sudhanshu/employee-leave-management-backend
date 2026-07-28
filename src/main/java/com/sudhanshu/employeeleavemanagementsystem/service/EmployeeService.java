package com.sudhanshu.employeeleavemanagementsystem.service;

import com.sudhanshu.employeeleavemanagementsystem.exception.ResourceNotFoundException;
import com.sudhanshu.employeeleavemanagementsystem.entity.Employee;
import com.sudhanshu.employeeleavemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.sudhanshu.employeeleavemanagementsystem.entity.LeaveBalance;
import com.sudhanshu.employeeleavemanagementsystem.repository.LeaveBalanceRepository;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
private LeaveBalanceRepository leaveBalanceRepository;

@Autowired
private PasswordEncoder passwordEncoder;

public Employee saveEmployee(Employee employee) {

    employee.setPassword(passwordEncoder.encode(employee.getPassword()));

    if (employee.getRole() == null || employee.getRole().isBlank()) {
        employee.setRole("EMPLOYEE");
    }

    Employee savedEmployee = employeeRepository.save(employee);

    LeaveBalance leaveBalance = new LeaveBalance();
    leaveBalance.setEmployee(savedEmployee);

    leaveBalanceRepository.save(leaveBalance);

    return savedEmployee;
}

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public Page<Employee> getEmployees(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
    
        return employeeRepository.findAll(pageable);
    }
    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository
                .findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        keyword,
                        keyword);
    }
    public List<Employee> sortEmployees(String field) {
        return employeeRepository.findAll(Sort.by(field));
    }
    public Employee getEmployeeById(Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id));
    
    }
    public Employee updateEmployee(Long id, Employee employee) {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id: " + id));
    
        existingEmployee.setFullName(employee.getFullName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setDesignation(employee.getDesignation());
        existingEmployee.setRole(employee.getRole());
    
        // Update password only if a new password is provided
        if (employee.getPassword() != null && !employee.getPassword().isBlank()) {
            existingEmployee.setPassword(
                    passwordEncoder.encode(employee.getPassword()));
        }
    
        return employeeRepository.save(existingEmployee);
    }
    public void deleteEmployee(Long id) {
    employeeRepository.deleteById(id);
    }
}