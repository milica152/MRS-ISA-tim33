package com.tim33.isa.repository;


import com.tim33.isa.model.FastFlightReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FastFlightReservationRepository extends JpaRepository<FastFlightReservation, Long> {


    List<FastFlightReservation> findAll();

    List<FastFlightReservation> findAllByFlightId(long id);

    FastFlightReservation findById(long id);

    void deleteById(long id);


}
