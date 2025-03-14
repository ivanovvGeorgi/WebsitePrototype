package com.example.boryanastherapy.repository;

import com.example.boryanastherapy.model.entity.Booking;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByDateAndTime(LocalDate date, LocalTime time);

    List<Booking> findByDate(LocalDate date);

    List<Booking> findAll(Sort sort);

    long countByDate(LocalDate date);

    @Query("SELECT DISTINCT b.date FROM Booking b")
    List<LocalDate> findBookedDates();

    @Query("SELECT b FROM Booking b WHERE (b.date = :today AND b.time > :currentTime) OR (b.date = :tomorrow AND b.time <= :targetTimeOfDay)")
    List<Booking> findBookingsWithin24Hours(
            @Param("today") LocalDate today,
            @Param("currentTime") LocalTime currentTime,
            @Param("tomorrow") LocalDate tomorrow,
            @Param("targetTimeOfDay") LocalTime targetTimeOfDay);
}
