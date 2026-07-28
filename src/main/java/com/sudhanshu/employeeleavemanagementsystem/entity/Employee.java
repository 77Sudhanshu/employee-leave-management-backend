package com.sudhanshu.employeeleavemanagementsystem.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

import java.util.List;
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String designation;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(name = "profile_image")
private String profileImage;

    private String otp;

private LocalDateTime otpExpiry;

@OneToMany(mappedBy = "employee")
@JsonIgnore
private List<LeaveRequest> leaveRequests;

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

public String getRole() {
    return role;
}

public void setRole(String role) {
    this.role = role;
}

    public List<LeaveRequest> getLeaveRequests() {
    return leaveRequests;
}

public void setLeaveRequests(List<LeaveRequest> leaveRequests) {
    this.leaveRequests = leaveRequests;
}
    public String getOtp() {
        return otp;
    }
    
    public void setOtp(String otp) {
        this.otp = otp;
    }
    
    public LocalDateTime getOtpExpiry() {
        return otpExpiry;
    }
    
    public void setOtpExpiry(LocalDateTime otpExpiry) {
        this.otpExpiry = otpExpiry;
    }
    public String getProfileImage() {
        return profileImage;
    }
    
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
