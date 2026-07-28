package com.sudhanshu.employeeleavemanagementsystem.controller;

import com.sudhanshu.employeeleavemanagementsystem.entity.Notification;
import com.sudhanshu.employeeleavemanagementsystem.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Notification> getMyNotifications() {
        return notificationService.getMyNotifications();
    }

    @GetMapping("/unread-count")
    public long getUnreadCount() {
        return notificationService.getUnreadCount();
    }
    @PutMapping("/{id}/read")
public String markAsRead(@PathVariable Long id) {
    notificationService.markAsRead(id);
    return "Notification marked as read.";
}

@PutMapping("/read-all")
public String markAllAsRead() {
    notificationService.markAllAsRead();
    return "All notifications marked as read.";
}
}