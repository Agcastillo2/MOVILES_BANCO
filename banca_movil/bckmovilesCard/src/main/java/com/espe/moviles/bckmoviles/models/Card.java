package com.espe.moviles.bckmoviles.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El número de tarjeta es obligatorio")
    @Pattern(regexp = "^[0-9]{16}$", message = "El número de tarjeta debe contener 16 dígitos")
    private String cardNumber;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/([0-9]{2})$", message = "La fecha de vencimiento debe estar en el formato MM/YY")
    private String expirationDate;

    @NotNull(message = "El código de seguridad es obligatorio")
    @Pattern(regexp = "^[0-9]{3}$", message = "El código de seguridad debe contener 3 dígitos")
    private String securityCode;

    @NotNull(message = "El tipo de tarjeta es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType cardType;

    @NotNull(message = "El saldo es obligatorio")
    @Min(value = 0, message = "El saldo no puede ser negativo")
    private Double balance;

    @NotNull(message = "El titular de la tarjeta es obligatorio")
    private String cardHolderName;

    @NotNull(message = "La fecha de creación es obligatoria")
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Enum para el tipo de tarjeta
    public enum CardType {
        CREDIT, DEBIT, PREPAID
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "El número de tarjeta es obligatorio") @Pattern(regexp = "^[0-9]{16}$", message = "El número de tarjeta debe contener 16 dígitos") String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(@NotNull(message = "El número de tarjeta es obligatorio") @Pattern(regexp = "^[0-9]{16}$", message = "El número de tarjeta debe contener 16 dígitos") String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public @NotNull(message = "La fecha de vencimiento es obligatoria") @Pattern(regexp = "^(0[1-9]|1[0-2])\\/([0-9]{2})$", message = "La fecha de vencimiento debe estar en el formato MM/YY") String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(@NotNull(message = "La fecha de vencimiento es obligatoria") @Pattern(regexp = "^(0[1-9]|1[0-2])\\/([0-9]{2})$", message = "La fecha de vencimiento debe estar en el formato MM/YY") String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public @NotNull(message = "El código de seguridad es obligatorio") @Pattern(regexp = "^[0-9]{3}$", message = "El código de seguridad debe contener 3 dígitos") String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(@NotNull(message = "El código de seguridad es obligatorio") @Pattern(regexp = "^[0-9]{3}$", message = "El código de seguridad debe contener 3 dígitos") String securityCode) {
        this.securityCode = securityCode;
    }

    public @NotNull(message = "El tipo de tarjeta es obligatorio") CardType getCardType() {
        return cardType;
    }

    public void setCardType(@NotNull(message = "El tipo de tarjeta es obligatorio") CardType cardType) {
        this.cardType = cardType;
    }

    public @NotNull(message = "El saldo es obligatorio") @Min(value = 0, message = "El saldo no puede ser negativo") Double getBalance() {
        return balance;
    }

    public void setBalance(@NotNull(message = "El saldo es obligatorio") @Min(value = 0, message = "El saldo no puede ser negativo") Double balance) {
        this.balance = balance;
    }

    public @NotNull(message = "El titular de la tarjeta es obligatorio") String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(@NotNull(message = "El titular de la tarjeta es obligatorio") String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public @NotNull(message = "La fecha de creación es obligatoria") LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NotNull(message = "La fecha de creación es obligatoria") LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
