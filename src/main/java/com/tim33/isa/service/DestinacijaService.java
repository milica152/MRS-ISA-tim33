package com.tim33.isa.service;

import com.tim33.isa.model.Destinacija;
import com.tim33.isa.repository.DestinacijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinacijaService {

    @Autowired
    DestinacijaRepository repository;

    public Destinacija save(Destinacija novaDestinacija){
        return repository.save(novaDestinacija);
    }

    public Destinacija update(Destinacija novaDestinacija){
        return repository.save(novaDestinacija);
    }

    public List<Destinacija> findAll(){
        return repository.findAll();
    }

    public Destinacija findByGrad(String name){
        return repository.findByGrad(name);
    }

}
