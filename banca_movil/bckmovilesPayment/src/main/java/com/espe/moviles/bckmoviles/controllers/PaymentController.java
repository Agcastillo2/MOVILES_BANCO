package com.espe.moviles.bckmoviles.controllers;

import com.espe.moviles.bckmoviles.models.DTO.TransactionDTO;
import com.espe.moviles.bckmoviles.models.Payment;
import com.espe.moviles.bckmoviles.models.relationship.PaymentTransaction;
import com.espe.moviles.bckmoviles.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.savePayment(payment);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{amount}/{paymentDate}")
    public ResponseEntity<Boolean> existsByAmountAndPaymentDate(@PathVariable Double amount, @PathVariable LocalDateTime paymentDate) {
        boolean exists = paymentService.existsByAmountAndPaymentDate(amount, paymentDate);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

        @PostMapping("/{paymentId}/transactionsOf/{transactionId}")
    public ResponseEntity<PaymentTransaction> assignTransaction(@PathVariable Long paymentId, @PathVariable Long transactionId) {
        Optional<PaymentTransaction> transactionAssigned = paymentService.assignTransaction(paymentId, transactionId);
        if (transactionAssigned.isPresent()) {
            return ResponseEntity.ok(transactionAssigned.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{paymentId}/transactionsOf/{transactionId}")
    public ResponseEntity<Void> deletePaymentTransaction(@PathVariable Long paymentId, @PathVariable Long transactionId) {
        paymentService.deletePaymentTransaction(paymentId, transactionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/transactionsOf/{transactionId}")
    public ResponseEntity<Payment> findPaymentByTransactionId(@PathVariable Long transactionId) {
        Optional<Payment> payment = paymentService.findPaymentByTransactionId(transactionId);
        if (payment.isPresent()) {
            return ResponseEntity.ok(payment.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{paymentId}/transactionsOf")
    public ResponseEntity<TransactionDTO> findTransactionByPaymentId(@PathVariable Long paymentId) {
        Optional<TransactionDTO> transaction = paymentService.findTransactionByPaymentId(paymentId);
        if (transaction.isPresent()) {
            return ResponseEntity.ok(transaction.get());
        }
        return ResponseEntity.notFound().build();
    }



}
