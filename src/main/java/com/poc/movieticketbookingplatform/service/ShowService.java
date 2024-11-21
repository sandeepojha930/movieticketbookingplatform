package com.poc.movieticketbookingplatform.service;

import com.poc.movieticketbookingplatform.model.Seat;
import com.poc.movieticketbookingplatform.model.Show;
import com.poc.movieticketbookingplatform.repository.SeatRepository;
import com.poc.movieticketbookingplatform.repository.ShowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing shows.
 */
@Service
public class ShowService {

    private static final Logger logger = LoggerFactory.getLogger(ShowService.class);

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private SeatRepository seatRepository;

    /**
     * Creates a new show.
     *
     * @param show the show details to be created
     * @return the created show
     */
    public Show createShow(Show show) {
        return showRepository.save(show);
    }

    /**
     * Updates an existing show.
     *
     * @param showId the ID of the show to be updated
     * @param updatedShow the updated show details
     * @return the updated show
     * @throws RuntimeException if the show is not found
     */
    public Show updateShow(Long showId, Show updatedShow) {
        Optional<Show> existingShow = showRepository.findById(showId);
        if (existingShow.isPresent()) {
            Show show = existingShow.get();
            show.setMovie(updatedShow.getMovie());
            show.setTheatre(updatedShow.getTheatre());
            show.setShowTime(updatedShow.getShowTime());
            show.setAvailableSeats(updatedShow.getAvailableSeats());
            return showRepository.save(show);
        } else {
            throw new RuntimeException("Show not found with id " + showId);
        }
    }

    /**
     * Deletes a show by its ID.
     *
     * @param showId the ID of the show to be deleted
     */
    public void deleteShow(Long showId) {
        showRepository.deleteById(showId);
    }

    /**
     * Allocates seat inventory for a show.
     *
     * @param showId the ID of the show
     * @param seats the list of seats to be allocated
     * @return the list of allocated seats
     */
    public List<Seat> allocateSeatInventory(Long showId, List<Seat> seats) {
        seats.forEach(seat -> {
            Show show = new Show();
            show.setId(showId);
            seat.setShow(show);
        });
        return seatRepository.saveAll(seats);
    }

    /**
     * Updates seat inventory for a show.
     *
     * @param showId the ID of the show
     * @param seats the list of seats to be updated
     * @return the list of updated seats
     */
    public List<Seat> updateSeatInventory(Long showId, List<Seat> seats) {
        logger.info("Updating seat inventory for show ID: {}", showId);
        seats.forEach(seat -> {
            Show show = new Show();
            show.setId(showId);
            seat.setShow(show);
        });
        return seatRepository.saveAll(seats);
    }
}