package com.espe.moviles.bckmoviles.services.impl;

import com.espe.moviles.bckmoviles.models.Notification;
import com.espe.moviles.bckmoviles.repositories.NotificationRepository;
import com.espe.moviles.bckmoviles.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public boolean existsByTitleAndMessage(String title, String message) {
        return notificationRepository.existsByTitleAndMessage(title, message);
    }
}
