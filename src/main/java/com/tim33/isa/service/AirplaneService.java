package com.tim33.isa.service;

import com.tim33.isa.model.Avion;
import com.tim33.isa.repository.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirplaneService {
    @Autowired
    AirplaneRepository repository;


    public Avion save(Avion newAirplane){
        // Manipulacija profilom...
        return repository.save(newAirplane);
    }

    public Avion update(Avion newAirplane){
        // Manipulacija profilom...
        return repository.save(newAirplane);
    }

    public List<Avion> findAll(){
        return repository.findAll();
    }

    public Avion findById(long id){
        return repository.findById(id);

    }
    public Avion findByACode(String naziv){
        return repository.findByACode(naziv);
    }


    public void deleteById(Long idDel) {
        repository.deleteById((long)idDel);
    }
}
