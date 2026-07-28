package com.sudhanshu.employeeleavemanagementsystem.service;

import com.sudhanshu.employeeleavemanagementsystem.entity.Employee;
import com.sudhanshu.employeeleavemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.sudhanshu.employeeleavemanagementsystem.dto.ChangePasswordRequest;

@Service
public class ProfileService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
private FileStorageService fileStorageService;

@Autowired
private PasswordEncoder passwordEncoder;

@Autowired
private NotificationService notificationService;

    public Employee getMyProfile() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
    public Employee uploadProfileImage(MultipartFile file) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
    
        String email = authentication.getName();
    
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    
        String contentType = file.getContentType();
    
        if (contentType == null ||
                !(contentType.equals("image/jpeg")
                        || contentType.equals("image/png")
                        || contentType.equals("image/jpg"))) {
    
            throw new RuntimeException("Only JPG and PNG images are allowed.");
        }
    
        String fileName = fileStorageService.storeProfileImage(file);
    
        employee.setProfileImage(fileName);
    
        Employee updated = employeeRepository.save(employee);

notificationService.createNotification(
        updated,
        "Profile Updated",
        "Your profile information has been updated successfully."
);

return updated;
    }
    public Path getProfileImage(String fileName) {
        return fileStorageService.loadProfileImage(fileName);
    }
    public Employee updateProfile(Employee updatedEmployee) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
    
        String email = authentication.getName();
    
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    
        employee.setFullName(updatedEmployee.getFullName());
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setDesignation(updatedEmployee.getDesignation());
    
        return employeeRepository.save(employee);
    }
    public String changePassword(ChangePasswordRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
    
        String email = authentication.getName();
    
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    
        // Check current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), employee.getPassword())) {
            throw new RuntimeException("Current password is incorrect.");
        }
    
        // Check new password and confirm password
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("New password and confirm password do not match.");
        }
    
        // Validate password length
        if (request.getNewPassword().length() < 8) {
            throw new RuntimeException("Password must be at least 8 characters long.");
        }
    
        // Prevent using the same password
        if (passwordEncoder.matches(request.getNewPassword(), employee.getPassword())) {
            throw new RuntimeException("New password must be different from the current password.");
        }
    
        employee.setPassword(passwordEncoder.encode(request.getNewPassword()));
    
        employeeRepository.save(employee);

notificationService.createNotification(
        employee,
        "Password Changed",
        "Your account password was changed successfully."
);

return "Password changed successfully.";
    }
}