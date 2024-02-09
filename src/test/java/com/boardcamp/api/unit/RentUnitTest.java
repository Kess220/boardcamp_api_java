package com.boardcamp.api.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.boardcamp.api.dtos.RentRequest;
import com.boardcamp.api.dtos.RentResponse;
import com.boardcamp.api.errors.BadRequestError;
import com.boardcamp.api.models.ClientModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentModel;
import com.boardcamp.api.repositories.ClientRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentRepository;
import com.boardcamp.api.services.RentService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

class RentUnitTest {

    @Mock
    private RentRepository rentRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ClientRepository clientRepository;

    private RentService rentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rentService = new RentService(rentRepository, gameRepository, clientRepository);
    }

    @SuppressWarnings("null")
    @Test
    void testCreateRent_success() {
        ClientModel clientData = new ClientModel();
        clientData.setId(1L);
        clientData.setName("Kaio");
        clientData.setCpf("00000000000");

        GameModel gameData = new GameModel();
        gameData.setId(2L);
        gameData.setName("Xadrez");
        gameData.setImage("http://www.imagem.com.br/xadrez.jpg");
        gameData.setStockTotal(10);
        gameData.setPricePerDay(5.0);

        RentRequest rentRequest = new RentRequest();
        rentRequest.setCustomerId(1L);
        rentRequest.setGameId(2L);
        rentRequest.setDaysRented(3);

        when(gameRepository.findById(gameData.getId())).thenReturn(Optional.of(gameData));

        when(clientRepository.findById(clientData.getId())).thenReturn(Optional.of(clientData));

        when(rentRepository.save(any(RentModel.class))).thenAnswer(invocation -> {
            RentModel rentModel = invocation.getArgument(0);
            rentModel.setId(1L);
            return rentModel;
        });

        RentResponse createdRent = rentService.createRent(rentRequest);

        assertNotNull(createdRent);
    }

    @Test
    void testCalculateDelayFee_withDelay() {
        RentModel rentData = new RentModel();
        rentData.setRentDate(LocalDate.now().minusDays(10));
        rentData.setDaysRented(5);
        rentData.setReturnDate(LocalDate.now());

        BigDecimal delayFee = rentService.calculateDelayFee(rentData);

        assertEquals(BigDecimal.valueOf(50), delayFee);
    }

    @SuppressWarnings("null")
    @Test
    void testReturnRental_alreadyReturned() {

        RentModel rentData = new RentModel();
        rentData.setId(1L);
        rentData.setReturnDate(LocalDate.now());

        when(rentRepository.findById(rentData.getId())).thenReturn(Optional.of(rentData));

        assertThrows(BadRequestError.class, () -> rentService.returnRental(rentData.getId()));
    }

}
