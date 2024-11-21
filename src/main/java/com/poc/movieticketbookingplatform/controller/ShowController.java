package com.poc.movieticketbookingplatform.controller;

import com.poc.movieticketbookingplatform.model.Show;
import com.poc.movieticketbookingplatform.model.Seat;
import com.poc.movieticketbookingplatform.service.ShowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing shows.
 */
@Api(value = "Show Management System")
@RestController
@RequestMapping("/shows")
public class ShowController {

    @Autowired
    private ShowService showService;

    /**
     * Endpoint to create a new show.
     *
     * @param show the show details to be created
     * @return the created show
     */
    @ApiOperation(value = "Create a new show", response = Show.class)
    @PostMapping
    public Show createShow(
            @ApiParam(value = "Show details to be created", required = true)
            @RequestBody Show show) {
        return showService.createShow(show);
    }

    /**
     * Endpoint to update an existing show.
     *
     * @param showId the ID of the show to be updated
     * @param updatedShow the updated show details
     * @return the updated show
     */
    @ApiOperation(value = "Update an existing show", response = Show.class)
    @PutMapping("/{showId}")
    public Show updateShow(
            @ApiParam(value = "ID of the show to be updated", required = true)
            @PathVariable Long showId,
            @ApiParam(value = "Updated show details", required = true)
            @RequestBody Show updatedShow) {
        return showService.updateShow(showId, updatedShow);
    }

    /**
     * Endpoint to delete a show.
     *
     * @param showId the ID of the show to be deleted
     */
    @ApiOperation(value = "Delete a show")
    @DeleteMapping("/{showId}")
    public void deleteShow(
            @ApiParam(value = "ID of the show to be deleted", required = true)
            @PathVariable Long showId) {
        showService.deleteShow(showId);
    }

    /**
     * Endpoint to allocate seat inventory for a show.
     *
     * @param showId the ID of the show
     * @param seats the list of seats to be allocated
     * @return the list of allocated seats
     */
    @ApiOperation(value = "Allocate seat inventory for a show", response = List.class)
    @PostMapping("/{showId}/seats")
    public List<Seat> allocateSeatInventory(
            @ApiParam(value = "ID of the show", required = true)
            @PathVariable Long showId,
            @ApiParam(value = "List of seats to be allocated", required = true)
            @RequestBody List<Seat> seats) {
        return showService.allocateSeatInventory(showId, seats);
    }

    /**
     * Endpoint to update seat inventory for a show.
     *
     * @param showId the ID of the show
     * @param seats the list of seats to be updated
     * @return the list of updated seats
     */
    @ApiOperation(value = "Update seat inventory for a show", response = List.class)
    @PutMapping("/{showId}/seats")
    public List<Seat> updateSeatInventory(
            @ApiParam(value = "ID of the show", required = true)
            @PathVariable Long showId,
            @ApiParam(value = "List of seats to be updated", required = true)
            @RequestBody List<Seat> seats) {
        return showService.updateSeatInventory(showId, seats);
    }
}