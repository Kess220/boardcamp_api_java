package com.boardcamp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boardcamp.api.models.ClientModel;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {
    boolean existsByCpf(String cpf);
}
