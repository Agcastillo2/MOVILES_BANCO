package com.espe.moviles.bckmoviles.models.relationship;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_notification")
@Data
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long notificationId;
    private String notificationTitle;
    private String notificationMessage;
    private String notificationType;
    private String notificationReadAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationReadAt() {
        return notificationReadAt;
    }

    public void setNotificationReadAt(String notificationReadAt) {
        this.notificationReadAt = notificationReadAt;
    }
}
