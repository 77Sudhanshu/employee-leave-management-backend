package com.sudhanshu.employeeleavemanagementsystem.controller;

import com.sudhanshu.employeeleavemanagementsystem.dto.EmployeeDashboardDto;
import com.sudhanshu.employeeleavemanagementsystem.service.EmployeeDashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee-dashboard")
@Tag(name = "Employee Dashboard", description = "Employee dashboard APIs")
public class EmployeeDashboardController {

    @Autowired
    private EmployeeDashboardService employeeDashboardService;

    @Operation(summary = "Get employee dashboard")
    @GetMapping("/{email}")
    public EmployeeDashboardDto getDashboard(@PathVariable String email) {
        return employeeDashboardService.getEmployeeDashboard(email);
    }
}