package com.poc.movieticketbookingplatform.controller;

import com.poc.movieticketbookingplatform.dto.BookingResponseDTO;
import com.poc.movieticketbookingplatform.model.Booking;
import com.poc.movieticketbookingplatform.dto.BookingRequestDTO;
import com.poc.movieticketbookingplatform.model.BookingResponse;
import com.poc.movieticketbookingplatform.service.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for managing bookings.
 */
@Api(value = "Booking Management System")
@RestController
@RequestMapping("api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    /**
     * Endpoint to book tickets.
     *
     * @param bookingRequestDTO the booking request containing details of the booking
     * @return the response entity containing the booking response
     */
    @ApiOperation(value = "Book tickets", response = BookingResponse.class)
    @PostMapping
    public ResponseEntity<BookingResponseDTO> bookTickets(
            @ApiParam(value = "Booking request containing details of the booking", required = true)
            @RequestBody BookingRequestDTO bookingRequestDTO) {
        BookingResponseDTO response = bookingService.bookTickets(bookingRequestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to cancel a booking.
     *
     * @param bookingId the ID of the booking to be canceled
     */
    @ApiOperation(value = "Cancel a booking")
    @DeleteMapping("/{bookingId}")
    public void cancelBooking(
            @ApiParam(value = "ID of the booking to be canceled", required = true)
            @PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
    }

    /**
     * Endpoint to bulk book tickets.
     *
     * @param bookings the list of bookings to be made
     * @return the list of bookings that were made
     */
    @ApiOperation(value = "Bulk book tickets", response = List.class)
    @PostMapping("/bulk")
    public List<Booking> bulkBookTickets(
            @ApiParam(value = "List of bookings to be made", required = true)
            @RequestBody List<Booking> bookings) {
        return bookingService.bulkBookTickets(bookings);
    }

    /**
     * Endpoint to bulk cancel bookings.
     *
     * @param bookingIds the list of booking IDs to be canceled
     */
    @ApiOperation(value = "Bulk cancel bookings")
    @DeleteMapping("/bulk")
    public void bulkCancelBookings(
            @ApiParam(value = "List of booking IDs to be canceled", required = true)
            @RequestBody List<Long> bookingIds) {
        bookingService.bulkCancelBookings(bookingIds);
    }

    @ApiOperation(value = "Apply discounts for bookings")
    @GetMapping("/applyDiscounts")
    public ResponseEntity<BookingResponseDTO> applyDiscounts(
            @RequestParam String city,
            @RequestParam String theatre,
            @RequestParam int numberOfTickets,
            @RequestParam LocalDateTime showTime) {
        BookingResponseDTO response = bookingService.applyDiscounts(city, theatre, numberOfTickets, showTime);
        return ResponseEntity.ok(response);
    }
}