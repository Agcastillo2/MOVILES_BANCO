package com.espe.moviles.bckmoviles.services.impl;

import com.espe.moviles.bckmoviles.models.Transaction;
import com.espe.moviles.bckmoviles.repositories.TransactionRepository;
import com.espe.moviles.bckmoviles.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public boolean existsByTransactionDateAndAmount(LocalDateTime transactionDate, Double amount) {
        return transactionRepository.existsByTransactionDateAndAmount(transactionDate, amount);
    }
}
