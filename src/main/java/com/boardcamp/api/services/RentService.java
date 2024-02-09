package com.boardcamp.api.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.ClientDTO;
import com.boardcamp.api.dtos.GameDTO;
import com.boardcamp.api.dtos.RentRequest;
import com.boardcamp.api.dtos.RentResponse;
import com.boardcamp.api.errors.BadRequestError;
import com.boardcamp.api.errors.NotFoundError;
import com.boardcamp.api.errors.UnavailableGamesError;
import com.boardcamp.api.models.ClientModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentModel;
import com.boardcamp.api.repositories.ClientRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentRepository;

import jakarta.transaction.Transactional;

@Service
public class RentService {

        private final RentRepository rentRepository;
        private final GameRepository gameRepository;
        private final ClientRepository clientRepository;

        public RentService(RentRepository rentRepository, GameRepository gameRepository,
                        ClientRepository clientRepository) {
                this.rentRepository = rentRepository;
                this.gameRepository = gameRepository;
                this.clientRepository = clientRepository;
        }

        @SuppressWarnings("null")
        @Transactional
        public RentResponse createRent(RentRequest rentRequest) {
                validateRentRequest(rentRequest);

                ClientModel customer = clientRepository.findById(rentRequest.getCustomerId())
                                .orElseThrow(() -> new NotFoundError("Customer not found"));

                GameModel game = gameRepository.findById(rentRequest.getGameId())
                                .orElseThrow(() -> new NotFoundError("Game not found"));

                int stockAvailable = game.getStockTotal();
                if (game.getRentals() != null) {
                        stockAvailable -= game.getRentals().size();
                }

                if (stockAvailable <= 0) {
                        throw new UnavailableGamesError("No games available for rental");
                }

                LocalDate rentDate = LocalDate.now();
                BigDecimal pricePerDayBigDecimal = BigDecimal.valueOf(game.getPricePerDay());
                BigDecimal originalPrice = calculateOriginalPrice(rentRequest.getDaysRented(), pricePerDayBigDecimal);

                RentModel rentData = new RentModel();
                rentData.setRentDate(rentDate);
                rentData.setDaysRented(rentRequest.getDaysRented());
                rentData.setOriginalPrice(originalPrice);
                rentData.setDelayFee(BigDecimal.ZERO);

                rentData.setCustomer(customer);
                rentData.setGame(game);

                RentModel savedRent = rentRepository.save(rentData);

                return mapRentModelToResponse(savedRent);
        }

        private void validateRentRequest(RentRequest rentRequest) {
                if (rentRequest.getDaysRented() <= 0) {
                        throw new BadRequestError("Number of rented days must be greater than 0");
                }

                if (rentRequest.getGameId() == null || rentRequest.getCustomerId() == null) {
                        throw new BadRequestError("GameId and CustomerId cannot be null");
                }
        }

        private BigDecimal calculateOriginalPrice(int daysRented, BigDecimal pricePerDay) {
                return BigDecimal.valueOf(daysRented).multiply(pricePerDay);
        }

        public RentResponse mapRentModelToResponse(RentModel rent) {
                return new RentResponse(
                                rent.getId(),
                                rent.getRentDate(),
                                rent.getDaysRented(),
                                rent.getReturnDate(),
                                rent.getOriginalPrice(),
                                rent.getDelayFee(),
                                new ClientDTO(
                                                rent.getCustomer().getId(),
                                                rent.getCustomer().getName(),
                                                rent.getCustomer().getCpf()),
                                new GameDTO(
                                                rent.getGame().getId(),
                                                rent.getGame().getName(),
                                                rent.getGame().getImage(),
                                                rent.getGame().getStockTotal(),
                                                rent.getGame().getPricePerDay()));
        }

        @Transactional
        public RentResponse returnRental(Long rentalId) {
                @SuppressWarnings("null")
                RentModel rent = rentRepository.findById(rentalId)
                                .orElseThrow(() -> new NotFoundError("Rental not found"));

                validateReturn(rent);

                rent.setReturnDate(LocalDate.now());

                BigDecimal delayFee = calculateDelayFee(rent);
                rent.setDelayFee(delayFee);

                RentModel savedRent = rentRepository.save(rent);

                return mapRentModelToResponse(savedRent);
        }

        private void validateReturn(RentModel rent) {
                if (rent.getReturnDate() != null) {
                        throw new BadRequestError("This rental has already been returned");
                }
        }

        public BigDecimal calculateDelayFee(RentModel rent) {
                LocalDate rentEndDate = rent.getRentDate().plusDays(rent.getDaysRented());
                long daysDelay = ChronoUnit.DAYS.between(rentEndDate, LocalDate.now());

                if (daysDelay <= 0) {
                        return BigDecimal.ZERO;
                }

                BigDecimal feePerDay = BigDecimal.valueOf(10);
                return feePerDay.multiply(BigDecimal.valueOf(daysDelay));
        }
}
