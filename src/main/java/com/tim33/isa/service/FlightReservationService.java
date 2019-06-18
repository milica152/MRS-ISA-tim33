package com.tim33.isa.service;

import com.tim33.isa.model.FlightReservation;
import com.tim33.isa.repository.FlightReservationRepository;
import com.tim33.isa.repository.LokacijaPresedanjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightReservationService {
    @Autowired
    FlightReservationRepository repository;

    public FlightReservation save(FlightReservation noviLet){
        // Manipulacija letom...
        return repository.save(noviLet);
    }

    public FlightReservation update(FlightReservation noviLet) {
        // Manipulacija letom...
        return repository.save(noviLet);
    }

    public List<FlightReservation> findAll() {
        return repository.findAll();
    }

    public FlightReservation findById(long id) {
        return repository.findById(id);
    }

    public List<FlightReservation> findAllFromFlight(long idFlight) {
        return repository.findAllByFlightId(idFlight);
    }

}
