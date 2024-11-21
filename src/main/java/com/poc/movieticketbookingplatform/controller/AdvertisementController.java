package com.poc.movieticketbookingplatform.controller;

import com.poc.movieticketbookingplatform.model.Advertisement;
import com.poc.movieticketbookingplatform.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    /**
     * Retrieves a list of all advertisements.
     *
     * @return a ResponseEntity containing the list of advertisements
     */
    @GetMapping
    public ResponseEntity<List<Advertisement>> getAllAdvertisements() {
        List<Advertisement> advertisements = advertisementService.getAllAdvertisements();
        return ResponseEntity.ok(advertisements);
    }

    /**
     * Retrieves an advertisement by its ID.
     *
     * @param id the ID of the advertisement to retrieve
     * @return a ResponseEntity containing the advertisement
     */
    @GetMapping("/{id}")
    public ResponseEntity<Advertisement> getAdvertisementById(@PathVariable Long id) {
        Advertisement advertisement = advertisementService.getAdvertisementById(id);
        return ResponseEntity.ok(advertisement);
    }

    /**
     * Creates a new advertisement.
     *
     * @param advertisement the advertisement to create
     * @return a ResponseEntity containing the created advertisement
     */
    @PostMapping
    public ResponseEntity<Advertisement> createAdvertisement(@RequestBody Advertisement advertisement) {
        Advertisement createdAdvertisement = advertisementService.createAdvertisement(advertisement);
        return ResponseEntity.ok(createdAdvertisement);
    }

    /**
     * Updates an existing advertisement.
     *
     * @param id the ID of the advertisement to update
     * @param advertisement the updated advertisement details
     * @return a ResponseEntity containing the updated advertisement
     */
    @PutMapping("/{id}")
    public ResponseEntity<Advertisement> updateAdvertisement(@PathVariable Long id, @RequestBody Advertisement advertisement) {
        Advertisement updatedAdvertisement = advertisementService.updateAdvertisement(id, advertisement);
        return ResponseEntity.ok(updatedAdvertisement);
    }

    /**
     * Deletes an advertisement by its ID.
     *
     * @param id the ID of the advertisement to delete
     * @return a ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable Long id) {
        advertisementService.deleteAdvertisement(id);
        return ResponseEntity.noContent().build();
    }
}