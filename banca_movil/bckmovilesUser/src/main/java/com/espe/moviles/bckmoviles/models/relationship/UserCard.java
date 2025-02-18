package com.espe.moviles.bckmoviles.models.relationship;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_card")
@Data
public class UserCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cardId;
    private String cardCardNumber;
    private String cardExpirationDate;
    private String cardSecurityCode;
    private String cardCardType;
    private Double cardBalance;
    private String cardCardHolderName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardCardNumber() {
        return cardCardNumber;
    }

    public void setCardCardNumber(String cardCardNumber) {
        this.cardCardNumber = cardCardNumber;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public String getCardSecurityCode() {
        return cardSecurityCode;
    }

    public void setCardSecurityCode(String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    public String getCardCardType() {
        return cardCardType;
    }

    public void setCardCardType(String cardCardType) {
        this.cardCardType = cardCardType;
    }

    public Double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(Double cardBalance) {
        this.cardBalance = cardBalance;
    }

    public String getCardCardHolderName() {
        return cardCardHolderName;
    }

    public void setCardCardHolderName(String cardCardHolderName) {
        this.cardCardHolderName = cardCardHolderName;
    }
}
