package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String cpf;

    private Long id;

    public ClientDTO() {
    }

    public ClientDTO(Long id, String name, String cpf) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
    }
}
