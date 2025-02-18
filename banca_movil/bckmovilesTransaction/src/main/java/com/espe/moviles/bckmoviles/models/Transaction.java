package com.espe.moviles.bckmoviles.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que 0")
    private Double amount;

    @NotNull(message = "La fecha de la transacción es obligatoria")
    private LocalDateTime transactionDate;

    @NotNull(message = "El tipo de transacción es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @NotNull(message = "La descripción de la transacción es obligatoria")
    private String description;

    @NotNull(message = "El estado de la transacción es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @PrePersist
    public void prePersist() {
        if (transactionDate == null) {
            this.transactionDate = LocalDateTime.now();
        }
    }

    // Enum para el tipo de transacción
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER, PAYMENT
    }

    // Enum para el estado de la transacción
    public enum TransactionStatus {
        PENDING, COMPLETED, FAILED, CANCELLED
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "El monto es obligatorio") @DecimalMin(value = "0.01", message = "El monto debe ser mayor que 0") Double getAmount() {
        return amount;
    }

    public void setAmount(@NotNull(message = "El monto es obligatorio") @DecimalMin(value = "0.01", message = "El monto debe ser mayor que 0") Double amount) {
        this.amount = amount;
    }

    public @NotNull(message = "La fecha de la transacción es obligatoria") LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(@NotNull(message = "La fecha de la transacción es obligatoria") LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public @NotNull(message = "El tipo de transacción es obligatorio") TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(@NotNull(message = "El tipo de transacción es obligatorio") TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public @NotNull(message = "La descripción de la transacción es obligatoria") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "La descripción de la transacción es obligatoria") String description) {
        this.description = description;
    }

    public @NotNull(message = "El estado de la transacción es obligatorio") TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "El estado de la transacción es obligatorio") TransactionStatus status) {
        this.status = status;
    }
}
