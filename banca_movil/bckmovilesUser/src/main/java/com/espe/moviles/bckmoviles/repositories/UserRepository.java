package com.espe.moviles.bckmoviles.repositories;

import com.espe.moviles.bckmoviles.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // Verificar si un usuario ya tiene un correo electrónico registrado

    Optional<User> findByUI(String UI);

    @Query("SELECT e FROM User e JOIN e.userCards ej WHERE ej.cardId = :cardId")
    List<User> findUsersByCardId(@Param("cardId") Long cardId);

    @Query("SELECT i FROM User i JOIN i.userNotifications ij WHERE ij.notificationId = :notificationId")
    List<User> findUsersByNotificationId(@Param("notificationId") Long notificationId);

    @Query("SELECT o FROM User o JOIN o.userPayments oj WHERE oj.paymentId = :paymentId")
    List<User> findUsersByPaymentId(@Param("paymentId") Long paymentId);
}
