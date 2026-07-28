package com.sudhanshu.employeeleavemanagementsystem.service;

import com.sudhanshu.employeeleavemanagementsystem.dto.EmployeeDashboardDto;
import com.sudhanshu.employeeleavemanagementsystem.entity.Employee;
import com.sudhanshu.employeeleavemanagementsystem.repository.EmployeeRepository;
import com.sudhanshu.employeeleavemanagementsystem.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sudhanshu.employeeleavemanagementsystem.entity.LeaveBalance;
import com.sudhanshu.employeeleavemanagementsystem.repository.LeaveBalanceRepository;

@Service
public class EmployeeDashboardService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
private LeaveBalanceRepository leaveBalanceRepository;

    public EmployeeDashboardDto getEmployeeDashboard(String email) {

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        EmployeeDashboardDto dto = new EmployeeDashboardDto();

        dto.setFullName(employee.getFullName());
        dto.setEmail(employee.getEmail());
        dto.setDepartment(employee.getDepartment());
        dto.setDesignation(employee.getDesignation());

        dto.setTotalLeaves(
                leaveRequestRepository.countByEmployeeId(employee.getId()));

        dto.setApprovedLeaves(
                leaveRequestRepository.countByEmployeeIdAndStatus(employee.getId(), "Approved"));

        dto.setPendingLeaves(
                leaveRequestRepository.countByEmployeeIdAndStatus(employee.getId(), "Pending"));

        dto.setRejectedLeaves(
                leaveRequestRepository.countByEmployeeIdAndStatus(employee.getId(), "Rejected"));

                LeaveBalance leaveBalance = leaveBalanceRepository
        .findByEmployeeId(employee.getId())
        .orElse(null);

if (leaveBalance != null) {

    dto.setCasualLeave(leaveBalance.getCasualLeave());

    dto.setSickLeave(leaveBalance.getSickLeave());

    dto.setEarnedLeave(leaveBalance.getEarnedLeave());

}
        return dto;
    }
}