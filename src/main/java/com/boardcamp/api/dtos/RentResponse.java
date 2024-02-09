package com.boardcamp.api.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentResponse {
    private Long id;
    private LocalDate rentDate;
    private int daysRented;
    private LocalDate returnDate;
    private BigDecimal originalPrice;
    private BigDecimal delayFee;
    private ClientDTO customer;
    private GameDTO game;

    public RentResponse(Long id, LocalDate rentDate, int daysRented, LocalDate returnDate,
            BigDecimal originalPrice, BigDecimal delayFee, ClientDTO customer, GameDTO game) {
        this.id = id;
        this.rentDate = rentDate;
        this.daysRented = daysRented;
        this.returnDate = returnDate;
        this.originalPrice = originalPrice;
        this.delayFee = delayFee;
        this.customer = customer;
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public BigDecimal getDelayFee() {
        return delayFee;
    }

    public ClientDTO getCustomer() {
        return customer;
    }

    public GameDTO getGame() {
        return game;
    }
}