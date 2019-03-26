package com.tim33.isa.service;

import com.tim33.isa.dto.filter.FilterPretrageVozila;
import com.tim33.isa.model.Vozilo;
import com.tim33.isa.repository.VozilaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VozilaService {

    @Autowired
    VozilaRepository repository;

    public Vozilo save(Vozilo novoVozilo) {
        // Manipulacija vozila...
        return repository.save(novoVozilo);
    }

    public Vozilo update(Vozilo novoVozilo) {
        // Manipulacija vozila...
        return repository.save(novoVozilo);
    }

    public List<Vozilo> findAll() {
        return repository.findAll();
    }

    public Vozilo findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Vozilo> findAllWithFilter(long idProfila, FilterPretrageVozila filter) {
        return repository.findAllWithFilter(idProfila, filter.getTipVozila());
    }

}