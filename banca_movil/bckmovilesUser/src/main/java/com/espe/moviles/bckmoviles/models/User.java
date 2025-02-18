package com.espe.moviles.bckmoviles.models;

import com.espe.moviles.bckmoviles.models.relationship.UserCard;
import com.espe.moviles.bckmoviles.models.relationship.UserNotification;
import com.espe.moviles.bckmoviles.models.relationship.UserPayment;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String UI;

    @NotNull(message = "El nombre es obligatorio")
    @Pattern(regexp = "^[A-Za-z]+$", message = "El nombre solo debe contener letras")
    private String firstName;

    @NotNull(message = "El apellido es obligatorio")
    @Pattern(regexp = "^[A-Za-z]+$", message = "El apellido solo debe contener letras")
    private String lastName;

    @NotNull(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@espe\\.edu\\.ec$", message = "El email debe terminar en @espe.edu.ec")
    private String email;

    @NotNull(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "La contraseña debe contener letras y números")
    private String password;

    @NotNull(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+593\\d{9}$", message = "El teléfono debe ser válido y de Ecuador")
    private String phoneNumber;

    @NotNull(message = "La dirección es obligatoria")
    private String address;

    @NotNull(message = "El estado de la cuenta es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus accountStatus;

    @NotNull(message = "La fecha de creación es obligatoria")
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<UserCard> userCards = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<UserNotification> userNotifications = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<UserPayment> userPayments = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Enum para el estado de la cuenta
    public enum AccountStatus {
        ACTIVE, INACTIVE, SUSPENDED
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUI() {
        return UI;
    }

    public void setUI(String UI) {
        this.UI = UI;
    }

    public @NotNull(message = "El nombre es obligatorio") @Pattern(regexp = "^[A-Za-z]+$", message = "El nombre solo debe contener letras") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull(message = "El nombre es obligatorio") @Pattern(regexp = "^[A-Za-z]+$", message = "El nombre solo debe contener letras") String firstName) {
        this.firstName = firstName;
    }

    public @NotNull(message = "El apellido es obligatorio") @Pattern(regexp = "^[A-Za-z]+$", message = "El apellido solo debe contener letras") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull(message = "El apellido es obligatorio") @Pattern(regexp = "^[A-Za-z]+$", message = "El apellido solo debe contener letras") String lastName) {
        this.lastName = lastName;
    }

    public @NotNull(message = "El email es obligatorio") @Email(message = "El email debe ser válido") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@espe\\.edu\\.ec$", message = "El email debe terminar en @espe.edu.ec") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "El email es obligatorio") @Email(message = "El email debe ser válido") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@espe\\.edu\\.ec$", message = "El email debe terminar en @espe.edu.ec") String email) {
        this.email = email;
    }

    public @NotNull(message = "La contraseña es obligatoria") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "La contraseña debe contener letras y números") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "La contraseña es obligatoria") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$", message = "La contraseña debe contener letras y números") String password) {
        this.password = password;
    }

    public @NotNull(message = "El teléfono es obligatorio") @Pattern(regexp = "^\\+593\\d{9}$", message = "El teléfono debe ser válido y de Ecuador") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotNull(message = "El teléfono es obligatorio") @Pattern(regexp = "^\\+593\\d{9}$", message = "El teléfono debe ser válido y de Ecuador") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @NotNull(message = "La dirección es obligatoria") String getAddress() {
        return address;
    }

    public void setAddress(@NotNull(message = "La dirección es obligatoria") String address) {
        this.address = address;
    }

    public @NotNull(message = "El estado de la cuenta es obligatorio") AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(@NotNull(message = "El estado de la cuenta es obligatorio") AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public @NotNull(message = "La fecha de creación es obligatoria") LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(message = "La fecha de creación es obligatoria") LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public List<UserCard> getUserCards() {
        return userCards;
    }

    public void setUserCards(List<UserCard> userCards) {
        this.userCards = userCards;
    }

    public List<UserNotification> getUserNotifications() {
        return userNotifications;
    }

    public void setUserNotifications(List<UserNotification> userNotifications) {
        this.userNotifications = userNotifications;
    }

    public List<UserPayment> getUserPayments() {
        return userPayments;
    }

    public void setUserPayments(List<UserPayment> userPayments) {
        this.userPayments = userPayments;
    }
}
