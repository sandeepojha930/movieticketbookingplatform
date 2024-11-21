package com.poc.movieticketbookingplatform.repository;

import com.poc.movieticketbookingplatform.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}