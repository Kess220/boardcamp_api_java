package com.boardcamp.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.boardcamp.api.dtos.RentRequest;
import com.boardcamp.api.dtos.RentResponse;
import com.boardcamp.api.services.RentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rentals")
public class RentController {

    private final RentService rentService;

    public RentController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping
    public ResponseEntity<RentResponse> createRent(@Valid @RequestBody RentRequest rentRequest) {
        RentResponse savedRent = rentService.createRent(rentRequest);

        return new ResponseEntity(savedRent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<RentResponse> returnRental(@PathVariable Long id) {
        RentResponse returnedRent = rentService.returnRental(id); 

        return ResponseEntity.ok(returnedRent);
    }
}
