package com.poc.movieticketbookingplatform.service;

import com.poc.movieticketbookingplatform.model.Movie;
import com.poc.movieticketbookingplatform.model.Seat;
import com.poc.movieticketbookingplatform.model.Show;
import com.poc.movieticketbookingplatform.repository.SeatRepository;
import com.poc.movieticketbookingplatform.repository.ShowRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShowServiceTest {

    @Mock
    private ShowRepository showRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private ShowService showService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createShow_shouldSaveAndReturnShow() {
        Show show = new Show();
        when(showRepository.save(show)).thenReturn(show);

        Show result = showService.createShow(show);

        assertEquals(show, result);
        verify(showRepository, times(1)).save(show);
    }

    @Test
    void updateShow_shouldUpdateAndReturnShow_whenShowExists() {
        Long showId = 1L;
        Show existingShow = new Show();
        Show updatedShow = new Show();
        Movie updatedMovie = new Movie();
        updatedMovie.setTitle("Updated Movie");
        updatedShow.setMovie(updatedMovie);
        when(showRepository.findById(showId)).thenReturn(Optional.of(existingShow));
        when(showRepository.save(existingShow)).thenReturn(existingShow);

        Show result = showService.updateShow(showId, updatedShow);

        assertEquals(updatedShow.getMovie(), result.getMovie());
        verify(showRepository, times(1)).findById(showId);
        verify(showRepository, times(1)).save(existingShow);
    }

    @Test
    void updateShow_shouldThrowException_whenShowDoesNotExist() {
        Long showId = 1L;
        Show updatedShow = new Show();
        when(showRepository.findById(showId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> showService.updateShow(showId, updatedShow));

        assertEquals("Show not found with id " + showId, exception.getMessage());
        verify(showRepository, times(1)).findById(showId);
        verify(showRepository, never()).save(any(Show.class));
    }

    @Test
    void deleteShow_shouldDeleteShow() {
        Long showId = 1L;

        showService.deleteShow(showId);

        verify(showRepository, times(1)).deleteById(showId);
    }

    @Test
    void allocateSeatInventory_shouldSaveAndReturnSeats() {
        Long showId = 1L;
        List<Seat> seats = Arrays.asList(new Seat(), new Seat());
        when(seatRepository.saveAll(seats)).thenReturn(seats);

        List<Seat> result = showService.allocateSeatInventory(showId, seats);

        assertEquals(seats, result);
        verify(seatRepository, times(1)).saveAll(seats);
    }

    @Test
    void updateSeatInventory_shouldUpdateAndReturnSeats() {
        Long showId = 1L;
        List<Seat> seats = Arrays.asList(new Seat(), new Seat());
        when(seatRepository.saveAll(seats)).thenReturn(seats);

        List<Seat> result = showService.updateSeatInventory(showId, seats);

        assertEquals(seats, result);
        verify(seatRepository, times(1)).saveAll(seats);
    }
}