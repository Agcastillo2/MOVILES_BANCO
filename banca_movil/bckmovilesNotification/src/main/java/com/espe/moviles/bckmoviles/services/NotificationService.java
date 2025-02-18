package com.espe.moviles.bckmoviles.services;

import com.espe.moviles.bckmoviles.models.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    Notification saveNotification(Notification notification);
    Optional<Notification> getNotificationById(Long id);
    List<Notification> getAllNotifications();
    void deleteNotification(Long id);
    boolean existsByTitleAndMessage(String title, String message);
}
