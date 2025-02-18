package com.espe.moviles.bckmoviles.repositories;

import com.espe.moviles.bckmoviles.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    boolean existsByTitleAndMessage(String title, String message); // Verificar si ya existe una notificación con el mismo título y mensaje
}
