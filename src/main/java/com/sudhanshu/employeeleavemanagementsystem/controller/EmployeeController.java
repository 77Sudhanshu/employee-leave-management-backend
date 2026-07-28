package com.sudhanshu.employeeleavemanagementsystem.controller;

import com.sudhanshu.employeeleavemanagementsystem.entity.Employee;
import com.sudhanshu.employeeleavemanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;

import java.util.List;

@Tag(name = "Employee APIs", description = "Operations related to employees")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "Create a new employee")
    @PostMapping
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @Operation(summary = "Get all employees")
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Employees fetched successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized"),
    @ApiResponse(responseCode = "403", description = "Access denied")
})
@GetMapping
public List<Employee> getAllEmployees() {
    return employeeService.getAllEmployees();
}
@GetMapping("/search")
public List<Employee> searchEmployees(
        @RequestParam String keyword) {

    return employeeService.searchEmployees(keyword);
}
@GetMapping("/sort")
public List<Employee> sortEmployees(
        @RequestParam String field) {

    return employeeService.sortEmployees(field);
}
@Operation(summary = "Get employee by ID")
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Employee found"),
    @ApiResponse(responseCode = "404", description = "Employee not found")
})
    @GetMapping("/{id}")
public Employee getEmployeeById(@PathVariable Long id) {
    return employeeService.getEmployeeById(id);
    }
    @GetMapping("/page")
public Page<Employee> getEmployeesWithPagination(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

    return employeeService.getEmployees(page, size);
}
    @Operation(summary = "Update employee")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @PutMapping("/{id}")
public Employee updateEmployee(@PathVariable Long id,
                               @RequestBody Employee employee) {

    return employeeService.updateEmployee(id, employee);
    }
    @Operation(summary = "Delete employee")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Employee deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @DeleteMapping("/{id}")
public String deleteEmployee(@PathVariable Long id) {

    employeeService.deleteEmployee(id);
    return "Employee deleted successfully!";
    }
}