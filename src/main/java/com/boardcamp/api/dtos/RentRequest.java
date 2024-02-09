package com.boardcamp.api.dtos;

import lombok.Data;

@Data
public class RentRequest {
    private Long customerId;
    private Long gameId;
    private int daysRented;
}
