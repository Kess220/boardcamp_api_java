package com.boardcamp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.boardcamp.api.models.GameModel;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<GameModel, Long> {

    @Override
    @NonNull
    List<GameModel> findAll();

    boolean existsByName(String nameGame);
}
