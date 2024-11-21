package com.poc.movieticketbookingplatform.service;

import com.poc.movieticketbookingplatform.dto.BookingDTO;
import com.poc.movieticketbookingplatform.dto.BookingRequestDTO;
import com.poc.movieticketbookingplatform.dto.BookingResponseDTO;
import com.poc.movieticketbookingplatform.model.*;
import com.poc.movieticketbookingplatform.repository.BookingRepository;
import com.poc.movieticketbookingplatform.repository.CityRepository;
import com.poc.movieticketbookingplatform.repository.RequestRepository;
import com.poc.movieticketbookingplatform.repository.TheatreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private TheatreRepository theatreRepository;

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void bookTickets_shouldReturnExistingResponse_whenRequestAlreadyProcessed() {
        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        bookingRequestDTO.setRequestId(1L);
        BookingResponse existingResponse = new BookingResponse();
        when(requestRepository.findById(1L)).thenReturn(Optional.of(existingResponse));

        BookingResponseDTO result = bookingService.bookTickets(bookingRequestDTO);

        assertEquals(existingResponse, result);
        verify(requestRepository, times(1)).findById(1L);
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void bookTickets_shouldSaveAndReturnBookingResponse_whenRequestIsNew() {
        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        bookingRequestDTO.setRequestId(1L);
        BookingDTO booking = new BookingDTO();
        bookingRequestDTO.setBooking(booking);
        Booking savedBooking = new Booking();
        when(requestRepository.findById(1L)).thenReturn(Optional.empty());
        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);

        BookingResponseDTO result = bookingService.bookTickets(bookingRequestDTO);

        assertNotNull(result);
        assertEquals(savedBooking.getId(), result.getBookingId());
        verify(requestRepository, times(1)).findById(1L);
        verify(requestRepository, times(1)).save(any(BookingResponse.class));
    }

    @Test
    void cancelBooking_shouldDeleteBookingById() {
        Long bookingId = 1L;

        bookingService.cancelBooking(bookingId);

        verify(bookingRepository, times(1)).deleteById(bookingId);
    }

    @Test
    void bulkBookTickets_shouldApplyDiscountsAndSaveBookings() {
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        Show show1 = new Show();
        Theatre theatre1 = new Theatre();
        theatre1.setCity("Eligible City");
        show1.setTheatre(theatre1);
        booking1.setShow(show1);
        booking2.setShow(show1);
        List<Booking> bookings = Arrays.asList(booking1, booking2);
        when(bookingRepository.saveAll(bookings)).thenReturn(bookings);

        List<Booking> result = bookingService.bulkBookTickets(bookings);

        assertEquals(bookings, result);
        verify(bookingRepository, times(1)).saveAll(bookings);
    }

    @Test
    void bulkCancelBookings_shouldDeleteAllBookingsById() {
        List<Long> bookingIds = Arrays.asList(1L, 2L);

        bookingService.bulkCancelBookings(bookingIds);

        verify(bookingRepository, times(1)).deleteAllById(bookingIds);
    }

    @Test
    void isEligibleForDiscount_shouldReturnTrue_whenBookingIsEligible() {
        Booking booking = new Booking();
        Show show = new Show();
        Theatre theatre = new Theatre();
        theatre.setCity("Eligible City");
        theatre.setName("Eligible Theatre");
        show.setTheatre(theatre);
        booking.setShow(show);
        City city = new City();
        city.setName("Eligible City");
        Theatre eligibleTheatre = new Theatre();
        eligibleTheatre.setName("Eligible Theatre");
        when(cityRepository.findAll()).thenReturn(Arrays.asList(city));
        when(theatreRepository.findAll()).thenReturn(Arrays.asList(eligibleTheatre));

        boolean result = bookingService.isEligibleForDiscount(booking);

        assertTrue(result);
    }

    @Test
    void isEligibleForDiscount_shouldReturnFalse_whenBookingIsNotEligible() {
        Booking booking = new Booking();
        Show show = new Show();
        Theatre theatre = new Theatre();
        theatre.setCity("Ineligible City");
        theatre.setName("Ineligible Theatre");
        show.setTheatre(theatre);
        booking.setShow(show);
        City city = new City();
        city.setName("Eligible City");
        Theatre eligibleTheatre = new Theatre();
        eligibleTheatre.setName("Eligible Theatre");
        when(cityRepository.findAll()).thenReturn(Arrays.asList(city));
        when(theatreRepository.findAll()).thenReturn(Arrays.asList(eligibleTheatre));

        boolean result = bookingService.isEligibleForDiscount(booking);

        assertFalse(result);
    }

    @Test
    void applyDiscounts_shouldApply50PercentDiscountOnThirdTicket() {
        Booking booking = new Booking();
        booking.setNumberOfSeats(3);
        booking.setTotalPrice(300.0);
        Show show = new Show();
        show.setShowTime(LocalDateTime.from(LocalTime.of(10, 0)));
        booking.setShow(show);

        bookingService.applyDiscounts(booking);

        assertEquals(250.0, booking.getTotalPrice());
    }

    @Test
    void applyDiscounts_shouldApply20PercentDiscountForAfternoonShows() {
        Booking booking = new Booking();
        booking.setNumberOfSeats(1);
        booking.setTotalPrice(100.0);
        Show show = new Show();
        show.setShowTime(LocalDateTime.from(LocalTime.of(13, 0)));
        booking.setShow(show);

        bookingService.applyDiscounts(booking);

        assertEquals(80.0, booking.getTotalPrice());
    }
}