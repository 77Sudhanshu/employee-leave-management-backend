package com.sudhanshu.employeeleavemanagementsystem.service;

import com.sudhanshu.employeeleavemanagementsystem.exception.ResourceNotFoundException;
import com.sudhanshu.employeeleavemanagementsystem.entity.LeaveRequest;
import com.sudhanshu.employeeleavemanagementsystem.repository.LeaveRequestRepository;
import com.sudhanshu.employeeleavemanagementsystem.entity.Employee;
import com.sudhanshu.employeeleavemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.temporal.ChronoUnit;
import com.sudhanshu.employeeleavemanagementsystem.entity.LeaveBalance;
import com.sudhanshu.employeeleavemanagementsystem.repository.LeaveBalanceRepository;

import java.util.List;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
private EmployeeRepository employeeRepository;

@Autowired
private EmailService emailService;

@Autowired
private EmailTemplateService emailTemplateService;

@Autowired
private FileStorageService fileStorageService;

@Autowired
private LeaveBalanceRepository leaveBalanceRepository;

@Autowired
private NotificationService notificationService;

    public LeaveRequest saveLeaveRequest(LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }
public LeaveRequest saveLeaveRequest(Long employeeId, LeaveRequest leaveRequest) {

    Employee employee = employeeRepository.findById(employeeId).orElse(null);

    if (employee != null) {
        leaveRequest.setEmployee(employee);
        return leaveRequestRepository.save(leaveRequest);
    }

    return null;
}
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    public LeaveRequest getLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id).orElse(null);
    }

    public LeaveRequest updateLeaveRequest(Long id, LeaveRequest leaveRequest) {

        LeaveRequest existingLeaveRequest = leaveRequestRepository.findById(id).orElse(null);

        if (existingLeaveRequest != null) {

            existingLeaveRequest.setLeaveType(leaveRequest.getLeaveType());
            existingLeaveRequest.setStartDate(leaveRequest.getStartDate());
            existingLeaveRequest.setEndDate(leaveRequest.getEndDate());
            existingLeaveRequest.setReason(leaveRequest.getReason());
            existingLeaveRequest.setStatus(leaveRequest.getStatus());

            return leaveRequestRepository.save(existingLeaveRequest);
        }

        return null;
    }
    public LeaveRequest approveLeave(Long id) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leave request not found with id: " + id));
    
        // Prevent approving twice
        if ("Approved".equalsIgnoreCase(leaveRequest.getStatus())) {
            throw new RuntimeException("Leave request is already approved.");
        }
    
        Employee employee = leaveRequest.getEmployee();
    
        LeaveBalance leaveBalance = leaveBalanceRepository
                .findByEmployeeId(employee.getId())
                .orElseThrow(() ->
                        new RuntimeException("Leave balance not found."));
    
        int leaveDays = (int) ChronoUnit.DAYS.between(
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate()) + 1;
    
        switch (leaveRequest.getLeaveType().toLowerCase()) {
    
            case "casual":
    
                if (leaveBalance.getCasualLeave() < leaveDays) {
                    throw new RuntimeException("Insufficient Casual Leave Balance");
                }
    
                leaveBalance.setCasualLeave(
                        leaveBalance.getCasualLeave() - leaveDays);
    
                break;
    
            case "sick":
    
                if (leaveBalance.getSickLeave() < leaveDays) {
                    throw new RuntimeException("Insufficient Sick Leave Balance");
                }
    
                leaveBalance.setSickLeave(
                        leaveBalance.getSickLeave() - leaveDays);
    
                break;
    
            case "earned":
    
                if (leaveBalance.getEarnedLeave() < leaveDays) {
                    throw new RuntimeException("Insufficient Earned Leave Balance");
                }
    
                leaveBalance.setEarnedLeave(
                        leaveBalance.getEarnedLeave() - leaveDays);
    
                break;
    
            default:
                throw new RuntimeException("Invalid Leave Type");
        }
    
        leaveBalanceRepository.save(leaveBalance);
    
        leaveRequest.setStatus("Approved");
    
        LeaveRequest savedLeave = leaveRequestRepository.save(leaveRequest);

        notificationService.createNotification(
            savedLeave.getEmployee(),
            "Leave Approved",
            "Your " + savedLeave.getLeaveType() +
            " leave has been approved."
    );
    
        String html = emailTemplateService.buildApprovalTemplate(savedLeave);
    
        emailService.sendHtmlEmail(
                savedLeave.getEmployee().getEmail(),
                "Leave Request Approved",
                html
        );
    
        return savedLeave;
    }

    public LeaveRequest rejectLeave(Long id) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Leave request not found with id: " + id));
    
        // Prevent rejecting twice
        if ("Rejected".equalsIgnoreCase(leaveRequest.getStatus())) {
            throw new RuntimeException("Leave request is already rejected.");
        }
    
        // Restore leave balance only if it was previously approved
        if ("Approved".equalsIgnoreCase(leaveRequest.getStatus())) {
    
            Employee employee = leaveRequest.getEmployee();
    
            LeaveBalance leaveBalance = leaveBalanceRepository
                    .findByEmployeeId(employee.getId())
                    .orElseThrow(() ->
                            new RuntimeException("Leave balance not found."));
    
            int leaveDays = (int) ChronoUnit.DAYS.between(
                    leaveRequest.getStartDate(),
                    leaveRequest.getEndDate()) + 1;
    
            switch (leaveRequest.getLeaveType().toLowerCase()) {
    
                case "casual":
    
                    leaveBalance.setCasualLeave(
                            leaveBalance.getCasualLeave() + leaveDays);
                    break;
    
                case "sick":
    
                    leaveBalance.setSickLeave(
                            leaveBalance.getSickLeave() + leaveDays);
                    break;
    
                case "earned":
    
                    leaveBalance.setEarnedLeave(
                            leaveBalance.getEarnedLeave() + leaveDays);
                    break;
    
                default:
                    throw new RuntimeException("Invalid Leave Type");
            }
    
            leaveBalanceRepository.save(leaveBalance);
        }
    
        leaveRequest.setStatus("Rejected");
    
        LeaveRequest savedLeave = leaveRequestRepository.save(leaveRequest);

        notificationService.createNotification(
            savedLeave.getEmployee(),
            "Leave Rejected",
            "Your " + savedLeave.getLeaveType() +
            " leave has been rejected."
    );
    
        emailService.sendEmail(
                savedLeave.getEmployee().getEmail(),
                "Leave Request Rejected",
                "Hello " + savedLeave.getEmployee().getFullName() +
                ",\n\nWe regret to inform you that your leave request has been REJECTED.\n\n" +
    
                "Leave Type : " + savedLeave.getLeaveType() + "\n" +
                "Start Date : " + savedLeave.getStartDate() + "\n" +
                "End Date   : " + savedLeave.getEndDate() + "\n" +
                "Reason     : " + savedLeave.getReason() +
    
                "\n\nThank you,\nHR Department"
        );
    
        return savedLeave;
    }
    
    public void deleteLeaveRequest(Long id) {
        leaveRequestRepository.deleteById(id);
    }
    public LeaveRequest uploadMedicalCertificate(Long leaveId, MultipartFile file) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found."));
    
        // Optional: Validate file type
        String contentType = file.getContentType();
    
        if (contentType == null ||
                !(contentType.equals("application/pdf")
                        || contentType.equals("image/jpeg")
                        || contentType.equals("image/png"))) {
    
            throw new RuntimeException("Only PDF, JPG, and PNG files are allowed.");
        }
    
        String fileName = fileStorageService.storeCertificate(file);
    
        leaveRequest.setMedicalCertificate(fileName);
    
        return leaveRequestRepository.save(leaveRequest);
    }
    public Path downloadMedicalCertificate(Long leaveId) {

        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found."));
    
        if (leaveRequest.getMedicalCertificate() == null) {
            throw new RuntimeException("No medical certificate uploaded.");
        }
    
        return fileStorageService.loadCertificate(leaveRequest.getMedicalCertificate());
    }
    public LeaveRequest applyLeave(LeaveRequest leaveRequest) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
    
        String email = authentication.getName();
    
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found"));
    
        leaveRequest.setEmployee(employee);
    
        leaveRequest.setStatus("Pending");
    
        return leaveRequestRepository.save(leaveRequest);
        
    }
    public List<LeaveRequest> getMyLeaveRequests() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
    
        String email = authentication.getName();
    
        return leaveRequestRepository.findByEmployeeEmail(email);
    }
    public List<LeaveRequest> getRecentLeaveRequests() {

        Pageable pageable = PageRequest.of(0, 5);
    
        return leaveRequestRepository.findAllByOrderByIdDesc(pageable);
    
    }
}