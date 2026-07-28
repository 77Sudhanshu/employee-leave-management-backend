package com.sudhanshu.employeeleavemanagementsystem.controller;

import com.sudhanshu.employeeleavemanagementsystem.dto.DashboardStats;
import com.sudhanshu.employeeleavemanagementsystem.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard APIs", description = "Dashboard statistics")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Operation(summary = "Get dashboard statistics")
    @GetMapping("/stats")
    public DashboardStats getDashboardStats() {
        return dashboardService.getDashboardStats();
    }
}