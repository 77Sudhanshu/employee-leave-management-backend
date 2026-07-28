package com.sudhanshu.employeeleavemanagementsystem.repository;

import com.sudhanshu.employeeleavemanagementsystem.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    long countByStatus(String status);

    long countByEmployeeId(Long employeeId);

    long countByEmployeeIdAndStatus(Long employeeId, String status);

    List<LeaveRequest> findByEmployeeEmail(String email);

    List<LeaveRequest> findAllByOrderByIdDesc(Pageable pageable);
}