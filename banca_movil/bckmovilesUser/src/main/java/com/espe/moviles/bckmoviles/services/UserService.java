package com.espe.moviles.bckmoviles.services;

import com.espe.moviles.bckmoviles.models.DTO.CardDTO;
import com.espe.moviles.bckmoviles.models.DTO.NotificationDTO;
import com.espe.moviles.bckmoviles.models.DTO.PaymentDTO;
import com.espe.moviles.bckmoviles.models.User;
import com.espe.moviles.bckmoviles.models.relationship.UserCard;
import com.espe.moviles.bckmoviles.models.relationship.UserNotification;
import com.espe.moviles.bckmoviles.models.relationship.UserPayment;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> getUserById(Long id);
    List<User> getAllUsers();
    void deleteUser(Long id);
    boolean existsByEmail(String email);


    Optional<UserCard> assignCard(Long userId, Long cardId);
    void deleteUserCard(Long userId, Long cardId);
    List<User> findUserByCardId(Long cardId);
    List<CardDTO> findCardByUserId(Long userId);

    Optional<UserNotification> assignNotification(Long userId, Long notificationId);
    void deleteUserNotification(Long userId, Long notificationId);
    List<User> findUserByNotificationId(Long notificationId);
    List<NotificationDTO> findNotificationByUserId(Long userId);


    Optional<UserPayment> assignPayment(Long userId, Long paymentId);
    void deleteUserPayment(Long userId, Long paymentId);
    List<User> findUserByPaymentId(Long paymentId);
    List<PaymentDTO> findPaymentByUserId(Long userId);

    Optional<User> getUserByUI(String UI);

}
