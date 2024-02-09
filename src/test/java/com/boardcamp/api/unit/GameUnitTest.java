package com.boardcamp.api.unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.boardcamp.api.errors.BodyRequestError;
import com.boardcamp.api.errors.NotFoundError;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.services.GameService;

class GameUnitTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        reset(gameRepository);
    }

    @Test
    void testGetGames_success() {
        List<GameModel> mockGames = new ArrayList<>();
        mockGames.add(new GameModel());
        mockGames.add(new GameModel());

        when(gameRepository.findAll()).thenReturn(mockGames);

        List<GameModel> retrievedGames = gameService.getGames();

        assertEquals(mockGames, retrievedGames);
    }

    @Test
    void testGetGames_notFound() {
        when(gameRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(NotFoundError.class, () -> gameService.getGames());
    }

    @Test
    void testCreateGame_success() {
        GameModel gameData = new GameModel();
        gameData.setName("Xadrez");
        gameData.setImage("http://www.imagem.com.br/xadrez.jpg");
        gameData.setStockTotal(10);
        gameData.setPricePerDay(5.0);

        when(gameRepository.save(gameData)).thenReturn(gameData);

        GameModel createdGame = gameService.createGame(gameData);

        assertEquals(gameData, createdGame);
    }

    @Test
    void testCreateGame_invalidData() {
        GameModel gameData = new GameModel();
        gameData.setImage("http://www.imagem.com.br/xadrez.jpg");
        gameData.setStockTotal(10);
        gameData.setPricePerDay(5.0);

        assertThrows(BodyRequestError.class, () -> gameService.createGame(gameData));
    }

    @Test
    void testCreateGame_duplicateName() {
        GameModel gameData = new GameModel();
        gameData.setName("Xadrez");
        gameData.setImage("http://www.imagem.com.br/xadrez.jpg");
        gameData.setStockTotal(10);
        gameData.setPricePerDay(5.0);

        when(gameRepository.existsByName(gameData.getName())).thenReturn(true);
    }
}