package com.espe.moviles.bckmoviles.services;

import com.espe.moviles.bckmoviles.models.DTO.TransactionDTO;
import com.espe.moviles.bckmoviles.models.Payment;
import com.espe.moviles.bckmoviles.models.relationship.PaymentTransaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Payment savePayment(Payment payment);
    Optional<Payment> getPaymentById(Long id);
    List<Payment> getAllPayments();
    void deletePayment(Long id);
    boolean existsByAmountAndPaymentDate(Double amount, LocalDateTime paymentDate);

    Optional<PaymentTransaction> assignTransaction(Long paymentId, Long transactionId);
    void deletePaymentTransaction(Long paymentId, Long transactionId);
    Optional<TransactionDTO> findTransactionByPaymentId(Long paymentId);
    Optional<Payment> findPaymentByTransactionId(Long transactionId);
}
