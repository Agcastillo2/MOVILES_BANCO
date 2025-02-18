package com.espe.moviles.bckmoviles.repositories;

import com.espe.moviles.bckmoviles.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByTransactionDateAndAmount(LocalDateTime transactionDate, Double amount); // Verificar transacciones con la misma fecha y monto


}
