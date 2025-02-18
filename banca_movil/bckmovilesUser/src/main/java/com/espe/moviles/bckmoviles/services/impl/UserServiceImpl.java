package com.espe.moviles.bckmoviles.services.impl;

import com.espe.moviles.bckmoviles.models.DTO.CardDTO;
import com.espe.moviles.bckmoviles.models.DTO.NotificationDTO;
import com.espe.moviles.bckmoviles.models.DTO.PaymentDTO;
import com.espe.moviles.bckmoviles.models.User;
import com.espe.moviles.bckmoviles.models.relationship.UserCard;
import com.espe.moviles.bckmoviles.models.relationship.UserNotification;
import com.espe.moviles.bckmoviles.models.relationship.UserPayment;
import com.espe.moviles.bckmoviles.repositories.UserRepository;
import com.espe.moviles.bckmoviles.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<UserCard> assignCard(Long userId, Long cardId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Validar si el card ya está asignado
            boolean cardYaAsignado = user.getUserCards().stream()
                    .anyMatch(userCard -> userCard.getCardId().equals(cardId));
            if (cardYaAsignado) {
                throw new IllegalArgumentException("Este card ya está asignado a este user");
            }

            try {
                // Llamada al microservicio de card
                CardDTO card = restTemplate.getForObject("http://localhost:8001/api/cards/" + cardId, CardDTO.class);

                if (card == null) {
                    throw new IllegalArgumentException("No se encuentra el card solicitado o no existe");
                }

                UserCard userCard = new UserCard();
                userCard.setCardId(card.getId());
                userCard.setCardCardNumber(card.getCardNumber());
                userCard.setCardExpirationDate(card.getExpirationDate());
                userCard.setCardSecurityCode(card.getSecurityCode());
                userCard.setCardCardType(card.getCardType());
                userCard.setCardBalance(card.getBalance());
                userCard.setCardCardHolderName(card.getCardHolderName());

                user.getUserCards().add(userCard);
                userRepository.save(user);

                return Optional.of(userCard);
            } catch (Exception e) {
                if (e.getMessage().contains("Connection refused")) {
                    throw new RuntimeException("Error en la base de datos");
                }
                throw new RuntimeException(e.getMessage());
            }
        }
        return Optional.empty();
    }

    public void deleteUserCard(Long userId, Long cardId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getUserCards().removeIf(userCard -> userCard.getCardId().equals(cardId));
            userRepository.save(user);
        }
    }

    public List<User> findUserByCardId(Long cardId) {
        return userRepository.findUsersByCardId(cardId);
    }

    public List<CardDTO> findCardByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<CardDTO> cards = new ArrayList<>();
            for (UserCard userCard : user.getUserCards()) {
                // Llamada al microservicio de cards para obtener los detalles del car
                CardDTO car = restTemplate.getForObject("http://localhost:8001/api/cards/" + userCard.getCardId(), CardDTO.class);
                if (car != null) {
                    cards.add(car);
                }
            }
            return cards;
        }
        return new ArrayList<>();
    }


    public Optional<UserNotification> assignNotification(Long userId, Long notificationId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Validar si el notification ya está asignado
            boolean notificationYaAsignado = user.getUserNotifications().stream()
                    .anyMatch(userNotification -> userNotification.getNotificationId().equals(notificationId));
            if (notificationYaAsignado) {
                throw new IllegalArgumentException("Este notification ya está asignado a este user");
            }

            try {
                // Llamada al microservicio de notification
                NotificationDTO notification = restTemplate.getForObject("http://localhost:8002/api/notifications/" + notificationId, NotificationDTO.class);

                if (notification == null) {
                    throw new IllegalArgumentException("No se encuentra el notification solicitado o no existe");
                }

                UserNotification userNotification = new UserNotification();
                userNotification.setNotificationId(notification.getId());
                userNotification.setNotificationTitle(notification.getTitle());
                userNotification.setNotificationMessage(notification.getMessage());
                userNotification.setNotificationType(notification.getNotificationType());
                userNotification.setNotificationReadAt(notification.getReadAt());

                user.getUserNotifications().add(userNotification);
                userRepository.save(user);

                return Optional.of(userNotification);
            } catch (Exception e) {
                if (e.getMessage().contains("Connection refused")) {
                    throw new RuntimeException("Error en la base de datos");
                }
                throw new RuntimeException(e.getMessage());
            }
        }
        return Optional.empty();
    }

    public void deleteUserNotification(Long userId, Long notificationId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getUserNotifications().removeIf(userNotification -> userNotification.getNotificationId().equals(notificationId));
            userRepository.save(user);
        }
    }

    public List<User> findUserByNotificationId(Long notificationId) {
        return userRepository.findUsersByNotificationId(notificationId);
    }

    public List<NotificationDTO> findNotificationByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<NotificationDTO> notifications = new ArrayList<>();
            for (UserNotification userNotification : user.getUserNotifications()) {
                // Llamada al microservicio de notifications para obtener los detalles del notification
                NotificationDTO notification = restTemplate.getForObject("http://localhost:8002/api/notifications/" + userNotification.getNotificationId(), NotificationDTO.class);
                if (notification != null) {
                    notifications.add(notification);
                }
            }
            return notifications;
        }
        return new ArrayList<>();
    }

    public Optional<UserPayment> assignPayment(Long userId, Long paymentId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Validar si el payment ya está asignado
            boolean paymentYaAsignado = user.getUserPayments().stream()
                    .anyMatch(userPayment -> userPayment.getPaymentId().equals(paymentId));
            if (paymentYaAsignado) {
                throw new IllegalArgumentException("Este payment ya está asignado a este user");
            }

            try {
                // Llamada al microservicio de payment
                PaymentDTO payment = restTemplate.getForObject("http://localhost:8003/api/payments/" + paymentId, PaymentDTO.class);

                if (payment == null) {
                    throw new IllegalArgumentException("No se encuentra el payment solicitado o no existe");
                }

                UserPayment userPayment = new UserPayment();
                userPayment.setPaymentId(payment.getId());
                userPayment.setPaymentAmount(payment.getAmount());
                userPayment.setPaymentDate(payment.getPaymentDate());
                userPayment.setPaymentDescription(payment.getDescription());
                userPayment.setPaymentStatus(payment.getStatus());

                user.getUserPayments().add(userPayment);
                userRepository.save(user);

                return Optional.of(userPayment);
            } catch (Exception e) {
                if (e.getMessage().contains("Connection refused")) {
                    throw new RuntimeException("Error en la base de datos");
                }
                throw new RuntimeException(e.getMessage());
            }
        }
        return Optional.empty();
    }

    public void deleteUserPayment(Long userId, Long paymentId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getUserPayments().removeIf(userPayment -> userPayment.getPaymentId().equals(paymentId));
            userRepository.save(user);
        }
    }

    public List<User> findUserByPaymentId(Long paymentId) {
        return userRepository.findUsersByPaymentId(paymentId);
    }

    public List<PaymentDTO> findPaymentByUserId(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<PaymentDTO> payments = new ArrayList<>();
            for (UserPayment userPayment : user.getUserPayments()) {
                // Llamada al microservicio de payments para obtener los detalles del payment
                PaymentDTO payment = restTemplate.getForObject("http://localhost:8003/api/payments/" + userPayment.getPaymentId(), PaymentDTO.class);
                if (payment != null) {
                    payments.add(payment);
                }
            }
            return payments;
        }
        return new ArrayList<>();
    }


    @Override
    public Optional<User> getUserByUI(String UI) {
        return userRepository.findByUI(UI);
    }

}
