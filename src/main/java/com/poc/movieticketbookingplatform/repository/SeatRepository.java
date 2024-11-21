package com.poc.movieticketbookingplatform.repository;

import com.poc.movieticketbookingplatform.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByShowTheatreIdAndSeatNumberAndIsBooked(Long theatreId, String seatNumber, boolean isBooked);
}