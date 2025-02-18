package com.espe.moviles.bckmoviles.models;

import com.espe.moviles.bckmoviles.models.relationship.PaymentTransaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor que 0")
    private Double amount;

    @NotNull(message = "La fecha del pago es obligatoria")
    private LocalDateTime paymentDate;

    @NotNull(message = "La descripción del pago es obligatoria")
    private String description;

    @NotNull(message = "El estado del pago es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "payment_id")
    private PaymentTransaction paymentTransactions;


    @PrePersist
    public void prePersist() {
        if (paymentDate == null) {
            this.paymentDate = LocalDateTime.now();
        }
    }

    // Enum para el estado del pago
    public enum PaymentStatus {
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

    public @NotNull(message = "La fecha del pago es obligatoria") LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(@NotNull(message = "La fecha del pago es obligatoria") LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public @NotNull(message = "La descripción del pago es obligatoria") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "La descripción del pago es obligatoria") String description) {
        this.description = description;
    }

    public @NotNull(message = "El estado del pago es obligatorio") PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "El estado del pago es obligatorio") PaymentStatus status) {
        this.status = status;
    }

}
