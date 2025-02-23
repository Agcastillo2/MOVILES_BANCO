package com.espe.moviles.bckmoviles.services;

import com.espe.moviles.bckmoviles.models.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    Card saveCard(Card card);
    Optional<Card> getCardById(Long id);
    List<Card> getAllCards();
    void deleteCard(Long id);
    boolean existsByCardNumber(String cardNumber);

    Card updateBalance(String cardNumber, Double amount);

}
