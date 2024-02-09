package com.boardcamp.api.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.boardcamp.api.dtos.ClientDTO;
import com.boardcamp.api.dtos.GameDTO;
import com.boardcamp.api.dtos.RentRequest;
import com.boardcamp.api.dtos.RentResponse;
import com.boardcamp.api.models.ClientModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.ClientRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RentIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private RentRepository rentRepository;

    @AfterEach
    void cleanDataBase() {
        rentRepository.deleteAll();
        gameRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @SuppressWarnings("null")
    @Test
    void testeCreateRent() {
        ClientModel clientData = new ClientModel();
        clientData.setName("Kaio");
        clientData.setCpf("00000000000");
        ResponseEntity<ClientModel> clientResponse = restTemplate.postForEntity("/customers", clientData,
                ClientModel.class);
        assertEquals(HttpStatus.CREATED, clientResponse.getStatusCode());

        GameModel game = new GameModel();
        game.setName("Chess");
        game.setImage("http://www.imagem.com.br/chess.jpg");
        game.setStockTotal(3);
        game.setPricePerDay(1.500);
        ResponseEntity<GameModel> gameResponse = restTemplate.postForEntity("/games", game, GameModel.class);
        assertEquals(HttpStatus.CREATED, gameResponse.getStatusCode());

        RentRequest rentRequest = new RentRequest();
        rentRequest.setCustomerId(clientResponse.getBody().getId());
        rentRequest.setGameId(gameResponse.getBody().getId());
        rentRequest.setDaysRented(3);

        ResponseEntity<RentResponse> rentResponse = restTemplate.postForEntity("/rentals", rentRequest,
                RentResponse.class);
        assertEquals(HttpStatus.OK, rentResponse.getStatusCode());

        RentResponse createdRent = rentResponse.getBody();
        assertNotNull(createdRent);
        assertNotNull(createdRent.getId());
        assertNotNull(createdRent.getRentDate());
        ClientDTO customerDto = createdRent.getCustomer();
        assertNotNull(customerDto);
        assertNotNull(customerDto.getId());
        assertNotNull(customerDto.getName());
        assertNotNull(customerDto.getCpf());
        GameDTO gameDto = createdRent.getGame();
        assertNotNull(gameDto);
        assertNotNull(gameDto.getId());
        assertNotNull(gameDto.getName());
        assertNotNull(gameDto.getImage());
        assertNotNull(gameDto.getStockTotal());
        assertNotNull(gameDto.getPricePerDay());
    }

    @SuppressWarnings("null")
    @Test
    void testeCreateRentInvalidId() {
        ClientModel clientData = new ClientModel();
        clientData.setName("Kaio");
        clientData.setCpf("00000000000");
        ResponseEntity<ClientModel> clientResponse = restTemplate.postForEntity("/customers", clientData,
                ClientModel.class);
        assertEquals(HttpStatus.CREATED, clientResponse.getStatusCode());

        GameModel game = new GameModel();
        game.setName("Chess");
        game.setImage("http://www.imagem.com.br/chess.jpg");
        game.setStockTotal(3);
        game.setPricePerDay(1.500);
        ResponseEntity<GameModel> gameResponse = restTemplate.postForEntity("/games", game, GameModel.class);
        assertEquals(HttpStatus.CREATED, gameResponse.getStatusCode());

        Long invalidId = 9L;

        RentRequest rentRequest = new RentRequest();
        rentRequest.setCustomerId(clientResponse.getBody().getId());
        rentRequest.setGameId(invalidId);
        rentRequest.setDaysRented(3);

        ResponseEntity<Void> rentResponse = restTemplate.postForEntity("/rentals", rentRequest,
                Void.class);
        assertEquals(HttpStatus.NOT_FOUND, rentResponse.getStatusCode());

    }

    @SuppressWarnings("null")
    @Test
    void testReturnRent() {
        ClientModel clientData = new ClientModel();
        clientData.setName("Kaio");
        clientData.setCpf("00000000000");
        ResponseEntity<ClientModel> clientResponse = restTemplate.postForEntity("/customers", clientData,
                ClientModel.class);
        assertEquals(HttpStatus.CREATED, clientResponse.getStatusCode());

        GameModel game = new GameModel();
        game.setName("Chess");
        game.setImage("http://www.imagem.com.br/chess.jpg");
        game.setStockTotal(3);
        game.setPricePerDay(1.500);
        ResponseEntity<GameModel> gameResponse = restTemplate.postForEntity("/games", game, GameModel.class);
        assertEquals(HttpStatus.CREATED, gameResponse.getStatusCode());

        RentRequest rentRequest = new RentRequest();
        rentRequest.setCustomerId(clientResponse.getBody().getId());
        rentRequest.setGameId(gameResponse.getBody().getId());
        rentRequest.setDaysRented(3);
        ResponseEntity<RentResponse> rentResponse = restTemplate.postForEntity("/rentals", rentRequest,
                RentResponse.class);
        assertEquals(HttpStatus.OK, rentResponse.getStatusCode());

        Long rentId = rentResponse.getBody().getId();

        ResponseEntity<RentResponse> returnResponse = restTemplate.exchange(
                "/rentals/{id}/return",
                HttpMethod.PUT,
                null,
                RentResponse.class,
                rentId);

        assertEquals(HttpStatus.OK, returnResponse.getStatusCode());

    }
    
    @SuppressWarnings("null")
    @Test
    void testReturnRentIdNotFound() {
        ClientModel clientData = new ClientModel();
        clientData.setName("Kaio");
        clientData.setCpf("00000000000");
        ResponseEntity<ClientModel> clientResponse = restTemplate.postForEntity("/customers", clientData,
                ClientModel.class);
        assertEquals(HttpStatus.CREATED, clientResponse.getStatusCode());

        GameModel game = new GameModel();
        game.setName("Chess");
        game.setImage("http://www.imagem.com.br/chess.jpg");
        game.setStockTotal(3);
        game.setPricePerDay(1.500);
        ResponseEntity<GameModel> gameResponse = restTemplate.postForEntity("/games", game, GameModel.class);
        assertEquals(HttpStatus.CREATED, gameResponse.getStatusCode());

        RentRequest rentRequest = new RentRequest();
        rentRequest.setCustomerId(clientResponse.getBody().getId());
        rentRequest.setGameId(gameResponse.getBody().getId());
        rentRequest.setDaysRented(3);
        ResponseEntity<RentResponse> rentResponse = restTemplate.postForEntity("/rentals", rentRequest,
                RentResponse.class);
        assertEquals(HttpStatus.OK, rentResponse.getStatusCode());

        Long rentId = 99L;

        ResponseEntity<Void> returnResponse = restTemplate.exchange(
                "/rentals/{id}/return",
                HttpMethod.PUT,
                null,
                Void.class,
                rentId);

        assertEquals(HttpStatus.NOT_FOUND, returnResponse.getStatusCode());

    }

}