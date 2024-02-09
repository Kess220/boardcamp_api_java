package com.boardcamp.api.repositories;

import com.boardcamp.api.dtos.RentResponse;
import com.boardcamp.api.models.RentModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RentRepository extends JpaRepository<RentModel, Long> {

    @Query("SELECT new com.boardcamp.api.dtos.RentResponse(r.id, r.rentDate, r.daysRented, r.returnDate, r.originalPrice, r.delayFee, c.id, c.name, c.cpf, g.id, g.name, g.image, g.stockTotal, g.pricePerDay) FROM RentModel r JOIN r.customer c JOIN r.game g WHERE r.id = :rentId")
    RentResponse findRentDetailsById(Long rentId);

    RentModel findById(long id);

    RentModel save(Optional<RentModel> rent);

}
