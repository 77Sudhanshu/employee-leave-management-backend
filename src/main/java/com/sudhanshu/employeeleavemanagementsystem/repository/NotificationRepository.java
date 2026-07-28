package com.sudhanshu.employeeleavemanagementsystem.repository;

import com.sudhanshu.employeeleavemanagementsystem.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByEmployeeEmailOrderByCreatedAtDesc(String email);

    long countByEmployeeEmailAndIsReadFalse(String email);
}