package com.tim33.isa.service;

import com.tim33.isa.model.LokacijaPresedanja;
import com.tim33.isa.repository.LokacijaPresedanjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LokacijaPresedanjaService {
    @Autowired
    LokacijaPresedanjaRepository repository;

    public LokacijaPresedanja save(LokacijaPresedanja novaLokacijaPresedanja){
        // Manipulacija profilom...
        return repository.save(novaLokacijaPresedanja);
    }

    public LokacijaPresedanja update(LokacijaPresedanja novaLokacijaPresedanja){
        // Manipulacija profilom...
        return repository.save(novaLokacijaPresedanja);
    }

    public List<LokacijaPresedanja> findAll(){
        return repository.findAll();
    }

    public LokacijaPresedanja findById(long id){
        return repository.findById(id).orElse(null);
    }

    public LokacijaPresedanja findByNazivAerodroma(String naziv){return repository.findByNazivAerodroma(naziv);}

    public void deleteById(long id) { repository.delete(findById(id)); }

}
