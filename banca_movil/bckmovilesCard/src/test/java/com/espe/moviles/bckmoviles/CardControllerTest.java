package com.espe.moviles.bckmoviles;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.espe.moviles.bckmoviles.controllers.CardController;
import com.espe.moviles.bckmoviles.models.Card;
import com.espe.moviles.bckmoviles.services.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
    }

    @Test
    public void testCreateCard() throws Exception {
        Card card = new Card();
        card.setCardNumber("1234567890123444");
        card.setBalance(500.0);
        card.setCardHolderName("John Doe");
        card.setCardType(Card.CardType.valueOf("CREDIT"));
        card.setExpirationDate("12/25");
        card.setSecurityCode("123");

        // Configura el mock para simular que la tarjeta no existe
        when(cardService.existsByCardNumber(anyString())).thenReturn(false);
        when(cardService.saveCard(any(Card.class))).thenReturn(card);

        mockMvc.perform(post("/api/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardNumber\": \"1234567890123444\", \"expirationDate\": \"12/25\", \"securityCode\": \"123\", \"cardType\": \"CREDIT\", \"balance\": 500.0, \"cardHolderName\": \"John Doe\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cardNumber").value("1234567890123444"))
                .andExpect(jsonPath("$.balance").value(500.0));

        // Verifica que saveCard fue invocado
        verify(cardService, times(1)).saveCard(any(Card.class));
    }

    @Test
    public void testGetCardById() throws Exception {
        Card card = new Card();
        card.setId(1L);
        card.setCardNumber("1234567890123444");
        card.setBalance(500.0);

        when(cardService.getCardById(1L)).thenReturn(Optional.of(card));

        mockMvc.perform(get("/api/cards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cardNumber").value("1234567890123444"))
                .andExpect(jsonPath("$.balance").value(500.0));

        verify(cardService, times(1)).getCardById(1L);
    }

    @Test
    public void testGetCardByIdNotFound() throws Exception {
        when(cardService.getCardById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cards/1"))
                .andExpect(status().isNotFound());

        verify(cardService, times(1)).getCardById(1L);
    }

    // Prueba para el endpoint getAllCards
    @Test
    public void testGetAllCards() throws Exception {
        Card card1 = new Card();
        card1.setId(1L);
        card1.setCardNumber("1234567890123444");

        Card card2 = new Card();
        card2.setId(2L);
        card2.setCardNumber("9876543210987654");

        List<Card> cards = Arrays.asList(card1, card2);

        when(cardService.getAllCards()).thenReturn(cards);

        mockMvc.perform(get("/api/cards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].cardNumber").value("1234567890123444"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].cardNumber").value("9876543210987654"));

        verify(cardService, times(1)).getAllCards();
    }

    // Prueba para el endpoint deleteCard
    @Test
    public void testDeleteCard() throws Exception {
        doNothing().when(cardService).deleteCard(1L);

        mockMvc.perform(delete("/api/cards/1"))
                .andExpect(status().isNoContent());

        verify(cardService, times(1)).deleteCard(1L);
    }

    // Prueba para el endpoint existsByCardNumber
    @Test
    public void testExistsByCardNumber() throws Exception {
        when(cardService.existsByCardNumber("1234567890123444")).thenReturn(true);

        mockMvc.perform(get("/api/cards/exists/1234567890123444"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(cardService, times(1)).existsByCardNumber("1234567890123444");
    }

    // Prueba para el endpoint updateBalance
    @Test
    public void testUpdateBalance() throws Exception {
        Card card = new Card();
        card.setCardNumber("1234567890123444");
        card.setBalance(1000.0);

        when(cardService.updateBalance("1234567890123444", 500.0)).thenReturn(card);

        mockMvc.perform(post("/api/cards/1234567890123444/balance")
                        .param("amount", "500.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardNumber").value("1234567890123444"))
                .andExpect(jsonPath("$.balance").value(1000.0));

        verify(cardService, times(1)).updateBalance("1234567890123444", 500.0);
    }
}
