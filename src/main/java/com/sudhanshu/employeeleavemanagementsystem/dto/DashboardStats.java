package com.sudhanshu.employeeleavemanagementsystem.dto;

public class DashboardStats {

    private long totalAdmins;
    private long totalEmployees;
    private long totalLeaveRequests;
    private long approvedLeaves;
    private long pendingLeaves;
    private long rejectedLeaves;

    public DashboardStats() {
    }

    public DashboardStats(long totalEmployees,
                          long totalLeaveRequests,
                          long approvedLeaves,
                          long pendingLeaves,
                          long rejectedLeaves) {
        this.totalEmployees = totalEmployees;
        this.totalLeaveRequests = totalLeaveRequests;
        this.approvedLeaves = approvedLeaves;
        this.pendingLeaves = pendingLeaves;
        this.rejectedLeaves = rejectedLeaves;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getTotalLeaveRequests() {
        return totalLeaveRequests;
    }

    public void setTotalLeaveRequests(long totalLeaveRequests) {
        this.totalLeaveRequests = totalLeaveRequests;
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
    public long getTotalAdmins() {
        return totalAdmins;
    }
    
    public void setTotalAdmins(long totalAdmins) {
        this.totalAdmins = totalAdmins;
    }
}