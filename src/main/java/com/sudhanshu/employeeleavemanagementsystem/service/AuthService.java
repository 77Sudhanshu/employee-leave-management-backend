package com.sudhanshu.employeeleavemanagementsystem.service;

import com.sudhanshu.employeeleavemanagementsystem.dto.RegisterRequest;
import com.sudhanshu.employeeleavemanagementsystem.dto.LoginRequest;
import com.sudhanshu.employeeleavemanagementsystem.dto.LoginResponse;
import com.sudhanshu.employeeleavemanagementsystem.entity.Employee;
import com.sudhanshu.employeeleavemanagementsystem.repository.EmployeeRepository;
import com.sudhanshu.employeeleavemanagementsystem.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
private EmailService emailService;

public LoginResponse login(LoginRequest request) {

    Employee employee = employeeRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid email"));

    if (!passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
        throw new RuntimeException("Invalid password");
    }

    String token = jwtService.generateToken(employee.getEmail());

    return new LoginResponse(
            token,
            employee.getRole(),
            employee.getFullName(),
            employee.getEmail()
    );
}
    public String register(RegisterRequest request) {

        if (employeeRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
    
        Employee employee = new Employee();
    
        employee.setFullName(request.getFullName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());
    
        // Encrypt password
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
    
        // Default role
        employee.setRole("EMPLOYEE");
    
        employeeRepository.save(employee);
    
        return "Employee registered successfully!";
    }
    public String forgotPassword(String email) {

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Email not found"));
    
        String otp = generateOtp();
    
        employee.setOtp(otp);
        employee.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
    
        employeeRepository.save(employee);
    
        emailService.sendEmail(
                employee.getEmail(),
                "Password Reset OTP",
                "Hello " + employee.getFullName() +
                        ",\n\nYour OTP for password reset is: " + otp +
                        "\n\nThis OTP is valid for 5 minutes." +
                        "\n\nHR Department"
        );
    
        return "OTP sent successfully.";
    }
    public String verifyOtp(String email, String otp) {

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Email not found"));
    
        if (employee.getOtp() == null) {
            throw new RuntimeException("OTP not generated.");
        }
    
        if (!employee.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP.");
        }
    
        if (employee.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired.");
        }
    
        return "OTP Verified Successfully.";
    }
    public String resetPassword(String email, String newPassword) {

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Email not found"));
    
        employee.setPassword(passwordEncoder.encode(newPassword));
    
        // Clear OTP after successful reset
        employee.setOtp(null);
        employee.setOtpExpiry(null);
    
        employeeRepository.save(employee);
    
        return "Password reset successfully.";
    }
    private String generateOtp() {

        Random random = new Random();
    
        int otp = 100000 + random.nextInt(900000);
    
        return String.valueOf(otp);
    }
}
