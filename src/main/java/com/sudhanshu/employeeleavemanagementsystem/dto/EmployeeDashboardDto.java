package com.sudhanshu.employeeleavemanagementsystem.dto;

public class EmployeeDashboardDto {

    private String fullName;
    private String email;
    private String department;
    private String designation;

    private long totalLeaves;
    private long approvedLeaves;
    private long pendingLeaves;
    private long rejectedLeaves;
    private int casualLeave;
private int sickLeave;
private int earnedLeave;

    public EmployeeDashboardDto() {
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

    public long getTotalLeaves() {
        return totalLeaves;
    }

    public void setTotalLeaves(long totalLeaves) {
        this.totalLeaves = totalLeaves;
    }

    public long getApprovedLeaves() {
        return approvedLeaves;
    }

    public void setApprovedLeaves(long approvedLeaves) {
        this.approvedLeaves = approvedLeaves;
    }

    public long getPendingLeaves() {
        return pendingLeaves;
    }

    public void setPendingLeaves(long pendingLeaves) {
        this.pendingLeaves = pendingLeaves;
    }

    public long getRejectedLeaves() {
        return rejectedLeaves;
    }

    public void setRejectedLeaves(long rejectedLeaves) {
        this.rejectedLeaves = rejectedLeaves;
    }
    public int getCasualLeave() {
        return casualLeave;
    }
    
    public void setCasualLeave(int casualLeave) {
        this.casualLeave = casualLeave;
    }
    
    public int getSickLeave() {
        return sickLeave;
    }
    
    public void setSickLeave(int sickLeave) {
        this.sickLeave = sickLeave;
    }
    
    public int getEarnedLeave() {
        return earnedLeave;
    }
    
    public void setEarnedLeave(int earnedLeave) {
        this.earnedLeave = earnedLeave;
    }
}