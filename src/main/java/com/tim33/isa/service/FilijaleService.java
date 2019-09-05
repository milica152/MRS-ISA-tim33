package com.tim33.isa.service;

import com.tim33.isa.model.Filijala;
import com.tim33.isa.repository.FilijaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilijaleService {
    
    @Autowired
    FilijaleRepository repository;

    public Filijala save(Filijala novaFilijala) {
        // Manipulacija vozila...
        return repository.save(novaFilijala);
    }

    public Filijala update(Filijala novaFilijala) {
        // Manipulacija vozila...
        return repository.save(novaFilijala);
    }

    public List<Filijala> findAll() {
        return repository.findAll();
    }

    public List<Filijala> findAllFromRC(long idProfila) {
        return repository.findAllByRentACarId(idProfila);
    }

    public Filijala findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
