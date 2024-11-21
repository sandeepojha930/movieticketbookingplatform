package com.poc.movieticketbookingplatform.dto;

import com.poc.movieticketbookingplatform.model.Booking;

public class BookingRequestDTO {
    private Long requestId;
    private BookingDTO booking;

    // Getters and Setters
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public BookingDTO getBooking() {
        return booking;
    }

    public void setBooking(BookingDTO booking) {
        this.booking = booking;
    }
}