package com.poc.movieticketbookingplatform.repository;

import com.poc.movieticketbookingplatform.model.BookingResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<BookingResponse, Long> {
    BookingResponse findByRequestId(Long requestId);
}