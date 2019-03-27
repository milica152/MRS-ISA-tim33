package com.tim33.isa.service;

import com.tim33.isa.model.RentACar;
import com.tim33.isa.repository.RentACarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentACarService {

    @Autowired
    RentACarRepository repository;

    public RentACar save(RentACar noviProfil) {
        // Manipulacija profilom...

        return repository.save(noviProfil);
    }

    public RentACar update(RentACar noviProfil) {
        // Manipulacija profilom...

        return repository.save(noviProfil);
    }

    public List<RentACar> findAll() {
        return repository.findAll();
    }

    public RentACar findById(long id) {
        return repository.findById(id).orElse(null);
    }

}
