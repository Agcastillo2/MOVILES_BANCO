package com.espe.moviles.bckmoviles.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El título de la notificación es obligatorio")
    private String title;

    @NotNull(message = "El mensaje de la notificación es obligatorio")
    private String message;

    @NotNull(message = "El tipo de notificación es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @NotNull(message = "La fecha de creación es obligatoria")
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull(message = "La fecha de lectura es obligatoria")
    private LocalDateTime readAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Enum para el tipo de notificación
    public enum NotificationType {
        TRANSACTION_ALERT, PAYMENT_REMINDER, PROMOTIONAL, ACCOUNT_UPDATE
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "El título de la notificación es obligatorio") String getTitle() {
        return title;
    }

    public void setTitle(@NotNull(message = "El título de la notificación es obligatorio") String title) {
        this.title = title;
    }

    public @NotNull(message = "El mensaje de la notificación es obligatorio") String getMessage() {
        return message;
    }

    public void setMessage(@NotNull(message = "El mensaje de la notificación es obligatorio") String message) {
        this.message = message;
    }

    public @NotNull(message = "El tipo de notificación es obligatorio") NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(@NotNull(message = "El tipo de notificación es obligatorio") NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public @NotNull(message = "La fecha de creación es obligatoria") LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(message = "La fecha de creación es obligatoria") LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public @NotNull(message = "La fecha de lectura es obligatoria") LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(@NotNull(message = "La fecha de lectura es obligatoria") LocalDateTime readAt) {
        this.readAt = readAt;
    }
}
