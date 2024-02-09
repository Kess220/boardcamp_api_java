package com.boardcamp.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.errors.BodyRequestError;
import com.boardcamp.api.errors.DuplicateGameNameError;
import com.boardcamp.api.errors.NotFoundError;
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
        List<GameModel> games = gameRepository.findAll();
        if (games.isEmpty()) {
            throw new NotFoundError("Nenhum jogo encontrado");
        }

        return games;
    }

    @Transactional
    public GameModel createGame(@Valid GameModel gameData) {
        if (gameData == null || StringUtils.isBlank(gameData.getName()) || StringUtils.isBlank(gameData.getImage())
                || gameData.getStockTotal() == null || gameData.getPricePerDay() == null) {
            throw new BodyRequestError(
                    "O corpo da requisição está incompleto ou contém campos vazios. Verifique os campos.");
        }

        if (gameData.getStockTotal() <= 0 || gameData.getPricePerDay() <= 0) {
            throw new BodyRequestError("Os campo stockTotal e pricePerDay tem que ser maiores que 0");
        }

        if (gameRepository.existsByName(gameData.getName())) {
            throw new DuplicateGameNameError("Já existe um jogo cadastrado com o mesmo nome");
        }
        return gameRepository.save(gameData);
    }

}
