package com.boardcamp.api.integration;

import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.GameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GameRepository gameRepository;

    @AfterEach
    void cleanDataBase() {
        gameRepository.deleteAll();
    }

    @Test
    void createdGameTest() {
        GameModel game = new GameModel();
        game.setName("Chess");
        game.setImage("http://www.imagem.com.br/chess.jpg");
        game.setStockTotal(3);
        game.setPricePerDay(1.500);

        ResponseEntity<GameModel> response = restTemplate.postForEntity("/games", game, GameModel.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        GameModel createdGame = response.getBody();
        assertNotNull(createdGame);
        assertEquals("Chess", createdGame.getName());
        assertEquals("http://www.imagem.com.br/chess.jpg", createdGame.getImage());
    }

    @Test
    void testEmptyName() {
        GameModel game = new GameModel();
        game.setImage("http://www.imagem.com.br/chess.jpg");
        game.setStockTotal(3);
        game.setPricePerDay(1.500);

        ResponseEntity<Void> response = restTemplate.postForEntity("/games", game, Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testGetAllGames() {
        GameModel game1 = new GameModel();
        game1.setName("Chess");
        game1.setImage("http://www.imagem.com.br/chess.jpg");
        game1.setStockTotal(3);
        game1.setPricePerDay(1.500);
        GameModel game2 = new GameModel();

        game2.setName("Uno");
        game2.setImage("http://www.imagem.com.br/uno.jpg");
        game2.setStockTotal(3);
        game2.setPricePerDay(1.500);

        ResponseEntity<GameModel> response1 = restTemplate.postForEntity("/games", game1, GameModel.class);
        ResponseEntity<GameModel> response2 = restTemplate.postForEntity("/games", game2, GameModel.class);
        assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        assertEquals(HttpStatus.CREATED, response2.getStatusCode());

        ResponseEntity<List<GameModel>> response = restTemplate.exchange("/games", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<GameModel>>() {
                });
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<GameModel> games = response.getBody();
        assertNotNull(games);
        assertEquals(2, games.size());
        assertEquals("Chess", games.get(0).getName());
        assertEquals("Uno", games.get(1).getName());
    }
}
