package com.espe.moviles.bckmoviles.controllers;

import com.espe.moviles.bckmoviles.models.DTO.CardDTO;
import com.espe.moviles.bckmoviles.models.DTO.NotificationDTO;
import com.espe.moviles.bckmoviles.models.DTO.PaymentDTO;
import com.espe.moviles.bckmoviles.models.User;
import com.espe.moviles.bckmoviles.models.relationship.UserCard;
import com.espe.moviles.bckmoviles.models.relationship.UserNotification;
import com.espe.moviles.bckmoviles.models.relationship.UserPayment;
import com.espe.moviles.bckmoviles.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser( @RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = userService.existsByEmail(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @PostMapping("/{userId}/cards/{cardId}")
    public ResponseEntity<UserCard> assignCard(@PathVariable Long userId, @PathVariable Long cardId) {
        Optional<UserCard> tratamientoAsignado = userService.assignCard(userId, cardId);
        if (tratamientoAsignado.isPresent()) {
            return ResponseEntity.ok(tratamientoAsignado.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}/cards/{cardId}")
    public ResponseEntity<Void> deleteUserCard(@PathVariable Long userId, @PathVariable Long cardId) {
        userService.deleteUserCard(userId, cardId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cards/{cardId}")
    public ResponseEntity<List<User>> findUserByCardId(@PathVariable Long cardId) {
        List<User> users = userService.findUserByCardId(cardId);
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}/cards")
    public ResponseEntity<List<CardDTO>> findCardByUserId(@PathVariable Long userId) {
        List<CardDTO> cards = userService.findCardByUserId(userId);
        if (!cards.isEmpty()) {
            return ResponseEntity.ok(cards);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{userId}/notifications/{notificationId}")
    public ResponseEntity<UserNotification> assignNotification(@PathVariable Long userId, @PathVariable Long notificationId) {
        Optional<UserNotification> tratamientoAsignado = userService.assignNotification(userId, notificationId);
        if (tratamientoAsignado.isPresent()) {
            return ResponseEntity.ok(tratamientoAsignado.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}/notifications/{notificationId}")
    public ResponseEntity<Void> deleteUserNotification(@PathVariable Long userId, @PathVariable Long notificationId) {
        userService.deleteUserNotification(userId, notificationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/notifications/{notificationId}")
    public ResponseEntity<List<User>> findUserByNotificationId(@PathVariable Long notificationId) {
        List<User> users = userService.findUserByNotificationId(notificationId);
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}/notifications")
    public ResponseEntity<List<NotificationDTO>> findNotificationByUserId(@PathVariable Long userId) {
        List<NotificationDTO> notifications = userService.findNotificationByUserId(userId);
        if (!notifications.isEmpty()) {
            return ResponseEntity.ok(notifications);
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/{userId}/payments/{paymentId}")
    public ResponseEntity<UserPayment> assignPayment(@PathVariable Long userId, @PathVariable Long paymentId) {
        Optional<UserPayment> tratamientoAsignado = userService.assignPayment(userId, paymentId);
        if (tratamientoAsignado.isPresent()) {
            return ResponseEntity.ok(tratamientoAsignado.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}/payments/{paymentId}")
    public ResponseEntity<Void> deleteUserPayment(@PathVariable Long userId, @PathVariable Long paymentId) {
        userService.deleteUserPayment(userId, paymentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<List<User>> findUserByPaymentId(@PathVariable Long paymentId) {
        List<User> users = userService.findUserByPaymentId(paymentId);
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}/payments")
    public ResponseEntity<List<PaymentDTO>> findPaymentByUserId(@PathVariable Long userId) {
        List<PaymentDTO> payments = userService.findPaymentByUserId(userId);
        if (!payments.isEmpty()) {
            return ResponseEntity.ok(payments);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/byUi/{UI}")
    public ResponseEntity<?> getUserByUI(@PathVariable String UI) {
        Optional<User> user = userService.getUserByUI(UI);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
