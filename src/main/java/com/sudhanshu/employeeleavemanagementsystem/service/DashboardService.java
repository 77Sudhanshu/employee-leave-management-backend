package com.sudhanshu.employeeleavemanagementsystem.service;

import com.sudhanshu.employeeleavemanagementsystem.dto.DashboardStats;
import com.sudhanshu.employeeleavemanagementsystem.repository.EmployeeRepository;
import com.sudhanshu.employeeleavemanagementsystem.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    public DashboardStats getDashboardStats() {

        DashboardStats stats = new DashboardStats();

        stats.setTotalEmployees(employeeRepository.count());

        stats.setTotalAdmins(employeeRepository.countByRole("ADMIN"));

        stats.setTotalLeaveRequests(leaveRequestRepository.count());

        stats.setApprovedLeaves(
                leaveRequestRepository.countByStatus("Approved"));

        stats.setRejectedLeaves(
                leaveRequestRepository.countByStatus("Rejected"));

        stats.setPendingLeaves(
                leaveRequestRepository.countByStatus("Pending"));

        return stats;
    }
}