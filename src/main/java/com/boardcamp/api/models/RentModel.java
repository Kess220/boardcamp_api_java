package com.boardcamp.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
public class RentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rent_date")
    private LocalDate rentDate;

    @Column(name = "days_rented")
    private int daysRented;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "original_price")
    private BigDecimal originalPrice;

    @Column(name = "delay_fee")
    private BigDecimal delayFee;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private ClientModel customer;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameModel game;

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setDaysRented(int daysRented) {
        this.daysRented = daysRented;
    }

    public int getDaysRented() {
        return daysRented;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setDelayFee(BigDecimal delayFee) {
        this.delayFee = delayFee;
    }

    public BigDecimal getDelayFee() {
        return delayFee;
    }

    public void setCustomer(ClientModel customer) {
        this.customer = customer;
    }

    public void setGame(GameModel game) {
        this.game = game;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public ClientModel getCustomer() {
        return customer;
    }

    public GameModel getGame() {
        return game;
    }

}
