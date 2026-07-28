package com.sudhanshu.employeeleavemanagementsystem.entity;

import jakarta.persistence.*;

@Entity
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int casualLeave = 12;

    private int sickLeave = 10;

    private int earnedLeave = 15;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public LeaveBalance() {
    }

    public Long getId() {
        return id;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}