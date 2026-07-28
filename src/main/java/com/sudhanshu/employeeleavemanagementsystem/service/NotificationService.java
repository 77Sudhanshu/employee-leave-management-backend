package com.sudhanshu.employeeleavemanagementsystem.service;

import com.sudhanshu.employeeleavemanagementsystem.entity.Employee;
import com.sudhanshu.employeeleavemanagementsystem.entity.Notification;
import com.sudhanshu.employeeleavemanagementsystem.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotification(Employee employee,
                                   String title,
                                   String message) {

        Notification notification = new Notification();

        notification.setEmployee(employee);
        notification.setTitle(title);
        notification.setMessage(message);

        notificationRepository.save(notification);
    }

    public List<Notification> getMyNotifications() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return notificationRepository
                .findByEmployeeEmailOrderByCreatedAtDesc(email);
    }

    public long getUnreadCount() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return notificationRepository
                .countByEmployeeEmailAndIsReadFalse(email);
    }
    public void markAsRead(Long id) {

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    
        notification.setRead(true);
    
        notificationRepository.save(notification);
    }
    
    public void markAllAsRead() {
    
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
    
        String email = authentication.getName();
    
        List<Notification> notifications =
                notificationRepository.findByEmployeeEmailOrderByCreatedAtDesc(email);
    
        for (Notification notification : notifications) {
            notification.setRead(true);
        }
    
        notificationRepository.saveAll(notifications);
    }
}