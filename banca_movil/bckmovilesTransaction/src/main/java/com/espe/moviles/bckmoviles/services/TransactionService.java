package com.espe.moviles.bckmoviles.services;

import com.espe.moviles.bckmoviles.models.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Transaction saveTransaction(Transaction transaction);
    Optional<Transaction> getTransactionById(Long id);
    List<Transaction> getAllTransactions();
    void deleteTransaction(Long id);
    boolean existsByTransactionDateAndAmount(LocalDateTime transactionDate, Double amount);
}
