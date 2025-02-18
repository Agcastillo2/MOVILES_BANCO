package com.espe.moviles.bckmoviles.controllers;

import com.espe.moviles.bckmoviles.models.Card;
import com.espe.moviles.bckmoviles.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody Card card) {
        // Verificar si el número de tarjeta ya existe antes de guardarla
        if (cardService.existsByCardNumber(card.getCardNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "El número de tarjeta ya está en uso"));
        }

        // Guardar la tarjeta si no está duplicada
        Card savedCard = cardService.saveCard(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable Long id) {
        Optional<Card> card = cardService.getCardById(id);
        return card.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> cards = cardService.getAllCards();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{cardNumber}")
    public ResponseEntity<Boolean> existsByCardNumber(@PathVariable String cardNumber) {
        boolean exists = cardService.existsByCardNumber(cardNumber);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}
