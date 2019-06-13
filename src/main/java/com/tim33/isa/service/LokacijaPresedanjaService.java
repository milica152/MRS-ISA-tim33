package com.tim33.isa.service;

import com.tim33.isa.model.LokacijaPresedanja;
import com.tim33.isa.repository.LokacijaPresedanjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LokacijaPresedanjaService {
    @Autowired
    LokacijaPresedanjaRepository repository;

    LokacijaPresedanja findByNazivAerodroma(String naziv){return repository.findByNazivAerodroma(naziv);}
}
