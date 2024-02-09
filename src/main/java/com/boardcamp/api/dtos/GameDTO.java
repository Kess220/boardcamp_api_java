package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class GameDTO {
    @NotEmpty(message = "Field 'name' is mandatory")
    private String name;

    private String image;
    private int stockTotal;
    private Double pricePerDay;
    private Long id;

    public GameDTO() {
    }

    public GameDTO(Long id, String name, String image, int stockTotal, Double pricePerDay) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.stockTotal = stockTotal;
        this.pricePerDay = pricePerDay;
    }

    public Double getPricePerDay() {
        return this.pricePerDay;
    }

}
