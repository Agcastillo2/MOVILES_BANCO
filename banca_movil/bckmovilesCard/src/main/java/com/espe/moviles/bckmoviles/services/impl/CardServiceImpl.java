package com.espe.moviles.bckmoviles.services.impl;

import com.espe.moviles.bckmoviles.models.Card;
import com.espe.moviles.bckmoviles.repositories.CardRepository;
import com.espe.moviles.bckmoviles.services.CardService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

    @Override
    public boolean existsByCardNumber(String cardNumber) {
        return cardRepository.existsByCardNumber(cardNumber);
    }

    @Override
    @Transactional
    public Card updateBalance(String cardNumber, Double amount) {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));

        double newBalance = card.getBalance() + amount;
        if (newBalance < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        card.setBalance(newBalance);
        return cardRepository.save(card);
    }
}
