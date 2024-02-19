package com.boardcamp.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.errors.BodyRequestError;
import com.boardcamp.api.errors.DuplicateGameNameError;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.GameRepository;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameModel> getGames() {
        return gameRepository.findAll();
    }

    @Transactional
    public GameModel createGame(@Valid GameModel gameData) {
        if (gameData == null || StringUtils.isBlank(gameData.getName()) || StringUtils.isBlank(gameData.getImage())
                || gameData.getStockTotal() == null || gameData.getPricePerDay() == null) {
            throw new BodyRequestError(
                    "The request body is incomplete or contains empty fields. Check the fields.");
        }

        if (gameData.getStockTotal() <= 0 || gameData.getPricePerDay() <= 0) {
            throw new BodyRequestError("The stockTotal and pricePerDay fields must be greater than 0");
        }

        if (gameRepository.existsByName(gameData.getName())) {
            throw new DuplicateGameNameError("There is already a game registered with the same name");
        }
        return gameRepository.save(gameData);
    }

}
