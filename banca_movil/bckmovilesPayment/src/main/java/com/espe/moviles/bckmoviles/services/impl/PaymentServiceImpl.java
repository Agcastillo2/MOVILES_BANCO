package com.espe.moviles.bckmoviles.services.impl;

import com.espe.moviles.bckmoviles.models.DTO.TransactionDTO;
import com.espe.moviles.bckmoviles.models.Payment;
import com.espe.moviles.bckmoviles.models.relationship.PaymentTransaction;
import com.espe.moviles.bckmoviles.repositories.PaymentRepository;
import com.espe.moviles.bckmoviles.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public boolean existsByAmountAndPaymentDate(Double amount, LocalDateTime paymentDate) {
        return paymentRepository.existsByAmountAndPaymentDate(amount, paymentDate);
    }


    public Optional<PaymentTransaction> assignTransaction(Long paymentId, Long transactionId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();

            // Validar si el transaction ya está asignado
            if (payment.getPaymentTransactions() != null && payment.getPaymentTransactions().getTransactionId().equals(transactionId)) {
                throw new IllegalArgumentException("Este transaction ya está asignado a este payment");
            }

            try {
                // Llamada al microservicio de transaction
                TransactionDTO transaction = restTemplate.getForObject("http://localhost:8004/api/transactions/" + transactionId, TransactionDTO.class);

                if (transaction == null) {
                    throw new IllegalArgumentException("No se encuentra el transaction solicitado o no existe");
                }

                // Crear el objeto PaymentTransaction
                PaymentTransaction paymentTransaction = new PaymentTransaction();
                paymentTransaction.setTransactionId(transaction.getId());
                paymentTransaction.setTransactionAmount(transaction.getAmount());
                paymentTransaction.setTransactionDate(transaction.getTransactionDate());
                paymentTransaction.setTransactionDescription(transaction.getDescription());
                paymentTransaction.setTransactionStatus(transaction.getStatus());

                // Asignar el transaction al payment (relación OneToOne)
                payment.setPaymentTransactions(paymentTransaction);
                paymentRepository.save(payment);

                return Optional.of(paymentTransaction);
            } catch (Exception e) {
                if (e.getMessage().contains("Connection refused")) {
                    throw new RuntimeException("Error en la base de datos");
                }
                throw new RuntimeException(e.getMessage());
            }
        }
        return Optional.empty();
    }

    public void deletePaymentTransaction(Long paymentId, Long transactionId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();

            // Validar si el transaction asignado es el correcto
            if (payment.getPaymentTransactions() != null && payment.getPaymentTransactions().getTransactionId().equals(transactionId)) {
                // Eliminar la relación, ya que es OneToOne
                payment.setPaymentTransactions(null);
                paymentRepository.save(payment);
            }
        }
    }

    public Optional<TransactionDTO> findTransactionByPaymentId(Long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();

            // Obtener el PaymentTransaction asignado
            PaymentTransaction paymentTransaction = payment.getPaymentTransactions();
            if (paymentTransaction != null) {
                // Llamada al microservicio de transactions para obtener los detalles del transaction
                TransactionDTO transaction = restTemplate.getForObject("http://localhost:8004/api/transactions/" + paymentTransaction.getTransactionId(), TransactionDTO.class);
                return Optional.ofNullable(transaction);
            }
        }
        return Optional.empty();  // Si no tiene ningún transaction asignado
    }

    public Optional<Payment> findPaymentByTransactionId(Long transactionId) {
        return paymentRepository.findPaymentByTransactionId(transactionId); // Suponiendo que tienes una consulta personalizada
    }


}
