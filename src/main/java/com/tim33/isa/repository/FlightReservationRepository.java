package com.tim33.isa.repository;

import com.tim33.isa.model.FlightReservation;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightReservationRepository extends JpaRepository<FlightReservation, Long> {
    List<FlightReservation> findAll();
    FlightReservation findById(long id);
    List<FlightReservation> findAllByFlightId(long id);

}
