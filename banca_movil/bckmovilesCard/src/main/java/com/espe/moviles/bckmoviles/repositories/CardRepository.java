package com.espe.moviles.bckmoviles.repositories;

import com.espe.moviles.bckmoviles.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByCardNumber(String cardNumber);
    Optional<Card> findByCardNumber(String cardNumber);// Verificar si ya existe una tarjeta con el mismo número
}
