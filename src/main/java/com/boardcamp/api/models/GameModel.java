package com.boardcamp.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GameModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Field 'name' is mandatory")
    private String name;

    @NotBlank(message = "Field 'image' is mandatory")
    private String image;

    @NotNull(message = "Field 'stockTotal' is mandatory")
    private Integer stockTotal;

    @NotNull(message = "Field 'pricePerDay' is mandatory")
    private Double pricePerDay;

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    private List<RentModel> rentals = new ArrayList<>();

}
