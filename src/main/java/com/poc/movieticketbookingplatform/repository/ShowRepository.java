package com.poc.movieticketbookingplatform.repository;

import com.poc.movieticketbookingplatform.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ShowRepository extends JpaRepository<Show, Long> {
    @Query("SELECT s FROM Show s WHERE s.theatre.city = :city AND s.theatre.name = :theatre AND s.showTime = :showTime")
    Show findByCityAndTheatreAndShowTime(@Param("city") String city, @Param("theatre") String theatre, @Param("showTime") LocalDateTime showTime);
}