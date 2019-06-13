package com.tim33.isa.service;

import com.tim33.isa.model.Aviokompanija;
import com.tim33.isa.repository.AviokompanijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AviokompanijaService {
    @Autowired
    AviokompanijaRepository repository;

    public Aviokompanija save(Aviokompanija noviProfil){
        // Manipulacija profilom...
        return repository.save(noviProfil);
    }

    public Aviokompanija update(Aviokompanija noviProfil){
        // Manipulacija profilom...
        return repository.save(noviProfil);
    }

    public List<Aviokompanija> findAll(){
        return repository.findAll();
    }

    public Aviokompanija findById(long id){
        return repository.findById(id);
    }

    public void deleteById(long id){
        repository.deleteById(id);
    }


}
