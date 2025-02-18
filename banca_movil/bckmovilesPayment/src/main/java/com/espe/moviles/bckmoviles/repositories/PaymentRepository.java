package com.espe.moviles.bckmoviles.repositories;

import com.espe.moviles.bckmoviles.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByAmountAndPaymentDate(Double amount, LocalDateTime paymentDate); // Verificar pagos con el mismo monto y fecha

    @Query("SELECT o FROM Payment o JOIN o.paymentTransactions oj WHERE oj.transactionId = :transactionId")
    Optional<Payment> findPaymentByTransactionId(@Param("transactionId") Long transactionId);

}
