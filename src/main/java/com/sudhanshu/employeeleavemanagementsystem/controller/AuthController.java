package com.sudhanshu.employeeleavemanagementsystem.controller;

import com.sudhanshu.employeeleavemanagementsystem.dto.RegisterRequest;
import com.sudhanshu.employeeleavemanagementsystem.dto.LoginRequest;
import com.sudhanshu.employeeleavemanagementsystem.dto.LoginResponse;
import com.sudhanshu.employeeleavemanagementsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import com.sudhanshu.employeeleavemanagementsystem.dto.ForgotPasswordRequest;
import com.sudhanshu.employeeleavemanagementsystem.dto.VerifyOtpRequest;
import com.sudhanshu.employeeleavemanagementsystem.dto.ResetPasswordRequest;

@Tag(name = "Authentication", description = "Login and Registration APIs")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Login user and generate JWT token")
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Login successful"),
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
})
@PostMapping("/login")
public LoginResponse login(@RequestBody LoginRequest request) {
    return authService.login(request);
}
@Operation(summary = "Register a new employee")
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Employee registered successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request")
})
@PostMapping("/register")
public String register(@Valid @RequestBody RegisterRequest request) {
    return authService.register(request);
}
@PostMapping("/forgot-password")
public String forgotPassword(@RequestBody ForgotPasswordRequest request) {

    return authService.forgotPassword(request.getEmail());
}
@PostMapping("/verify-otp")
public String verifyOtp(@RequestBody VerifyOtpRequest request) {

    return authService.verifyOtp(
            request.getEmail(),
            request.getOtp()
    );
}
@PostMapping("/reset-password")
public String resetPassword(@RequestBody ResetPasswordRequest request) {

    return authService.resetPassword(
            request.getEmail(),
            request.getNewPassword()
    );
}
}