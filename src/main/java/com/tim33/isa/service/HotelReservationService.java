package com.tim33.isa.service;

import com.tim33.isa.model.HotelReservation;
import com.tim33.isa.repository.HotelReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelReservationService {

    @Autowired
    HotelReservationRepository repository;

    public HotelReservation save(HotelReservation novaRezervacija) {
        return repository.save(novaRezervacija);
    }

    public HotelReservation update(HotelReservation novaRezervacija) {
        return repository.save(novaRezervacija);
    }

    public List<HotelReservation> findAll() {
        return repository.findAll();
    }

    public HotelReservation findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(long id) { repository.delete(findById(id)); }
}
