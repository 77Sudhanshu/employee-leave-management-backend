package com.sudhanshu.employeeleavemanagementsystem.repository;

import com.sudhanshu.employeeleavemanagementsystem.entity.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {

    Optional<LeaveBalance> findByEmployeeId(Long employeeId);

}