package com.poc.movieticketbookingplatform.service;

import com.poc.movieticketbookingplatform.dto.BookingDTO;
import com.poc.movieticketbookingplatform.dto.BookingRequestDTO;
import com.poc.movieticketbookingplatform.dto.BookingResponseDTO;
import com.poc.movieticketbookingplatform.model.*;
import com.poc.movieticketbookingplatform.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;


    @Transactional
    public BookingResponseDTO bookTickets(BookingRequestDTO bookingRequestDTO) {
        BookingResponse existingResponse = requestRepository.findByRequestId(bookingRequestDTO.getRequestId());
        if (existingResponse != null) {
            return convertToDTO(existingResponse);
        }

        BookingDTO booking = bookingRequestDTO.getBooking();
        Booking bookingEntity = convertToEntity(booking);

        Booking savedBooking = bookingRepository.save(bookingEntity);

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setRequestId(bookingRequestDTO.getRequestId());
        bookingResponse.setBooking(savedBooking);
        requestRepository.save(bookingResponse);

        return convertToDTO(bookingResponse);
    }

    private Booking convertToEntity(BookingDTO bookingDTO) {
        // Create a new Booking entity
        Booking entity = new Booking();

        // Set the booking time from the DTO
        entity.setBookingTime(bookingDTO.getBookingDate());

        // Find the Show using the show ID from the DTO, throw exception if not found
        entity.setShow(showRepository.findById(bookingDTO.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found with ID " + bookingDTO.getShowId())));
        // Save the booking entity first
        Booking savedBooking = bookingRepository.save(entity);
        // Map the seats from the bookingDTO to the entity, checking availability
        List<Seat> seats = bookingDTO.getSeats().stream()
                .map(seatId -> {
                    // Check if the seat is available (not booked)
                    Seat seat = seatRepository.findByShowTheatreIdAndSeatNumberAndIsBooked(
                                    bookingDTO.getTheatreId(), seatId, false)
                            .orElseThrow(() -> new RuntimeException("Seat " + seatId + " not available or already booked"));

                    // Set the booking and show for the seat
                    seat.setBooking(savedBooking);
                    seat.setShow(savedBooking.getShow());
                    seat.setBooked(true);
                    return seat;
                })
                .collect(Collectors.toList());

        // If no available seats were found, throw an error
        if (seats.isEmpty()) {
            throw new RuntimeException("No available seats for the requested show");
        }

        // Set the seats and number of seats in the booking entity
        savedBooking.setSeats(seats);
        savedBooking.setNumberOfSeats(seats.size());

        // Apply any discounts for the booking
        BookingResponseDTO bookingResponseDTO = applyDiscounts(
                savedBooking.getShow().getTheatre().getCity(),
                savedBooking.getShow().getTheatre().getName(),
                savedBooking.getNumberOfSeats(),
                savedBooking.getShow().getShowTime());

        // Set the total price after applying discounts
        savedBooking.setTotalPrice(bookingResponseDTO.getTotalPrice());

        return bookingRepository.save(savedBooking);
    }


    @Transactional
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public List<Booking> bulkBookTickets(List<Booking> bookings) {
        bookings.forEach(booking -> {
            if (isEligibleForDiscount(booking)) {
                applyDiscounts(booking);
            }
        });
        return bookingRepository.saveAll(bookings);
    }

    public void applyDiscounts(Booking booking) {
        logger.info("Applying discounts for booking: {}", booking);
        int numberOfSeats = booking.getNumberOfSeats();
        LocalTime showTime = booking.getShow().getShowTime().toLocalTime();
        double totalPrice = booking.getTotalPrice();

        double discountedPrice = calculateDiscountedPrice(totalPrice, numberOfSeats, showTime);
        booking.setTotalPrice(discountedPrice);
    }

    public void bulkCancelBookings(List<Long> bookingIds) {
        logger.info("Bulk canceling bookings with IDs: {}", bookingIds);
        bookingRepository.deleteAllById(bookingIds);
    }

    public boolean isEligibleForDiscount(Booking booking) {
        String city = booking.getShow().getTheatre().getCity();
        String theatreName = booking.getShow().getTheatre().getName();
        return isEligibleForDiscount(city, theatreName);
    }

    public BookingResponseDTO applyDiscounts(String city, String theatre, int numberOfTickets, LocalDateTime showTime) {
        BookingResponseDTO response = new BookingResponseDTO();
        Show show = showRepository.findByCityAndTheatreAndShowTime(city, theatre, showTime);
        double basePricePerTicket = show.getPrice();
        double totalPrice = basePricePerTicket * numberOfTickets;
        response.setTotalPrice(totalPrice);

        // Check if the city and theatre are eligible for discounts
        if (isEligibleForDiscount(city, theatre)) {
            double discountedPrice = calculateDiscountedPrice(totalPrice, numberOfTickets, showTime.toLocalTime());
            response.setTotalPrice(discountedPrice);
        }

        return response;
    }

    private boolean isEligibleForDiscount(String city, String theatre) {
        List<String> eligibleCities = cityRepository.findAll().stream()
                .map(City::getName)
                .collect(Collectors.toList());

        List<String> eligibleTheatres = theatreRepository.findAll().stream()
                .map(Theatre::getName)
                .collect(Collectors.toList());

        return eligibleCities.contains(city) && eligibleTheatres.contains(theatre);
    }

    private double calculateDiscountedPrice(double totalPrice, int numberOfTickets, LocalTime showTime) {
        // Apply 50% discount on the third ticket
        if (numberOfTickets >= 3) {
            double discount = totalPrice * 0.5 / numberOfTickets;
            totalPrice -= discount;
        }

        // Apply 20% discount for afternoon shows (12 PM to 4 PM)
        if (showTime.isAfter(LocalTime.of(12, 0)) && showTime.isBefore(LocalTime.of(16, 0))) {
            double discount = totalPrice * 0.2;
            totalPrice -= discount;
        }

        return totalPrice;
    }

    private BookingResponseDTO convertToDTO(BookingResponse bookingResponse) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(bookingResponse.getId());
        dto.setRequestId(bookingResponse.getRequestId());
        dto.setBookingId(bookingResponse.getBooking().getId());
        dto.setTotalPrice(bookingResponse.getBooking().getTotalPrice());
        return dto;
    }
}