package com.tim33.isa.service;

import com.tim33.isa.dto.filter.FilterPretrageRCServisa;
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

    public List<RentACar> findAllWithFilter(FilterPretrageRCServisa filter) {
        return repository.findAllWithFilter(filter);
    }

    public RentACar findById(long id) {
        return repository.findById(id).orElse(null);
    }
    public RentACar findByNaziv(String naziv){
        return repository.findByNaziv(naziv);
    }

    public String checkRCS(RentACar rentACar){
        if (rentACar.getAdresa().isEmpty()||rentACar.getNaziv().isEmpty()||rentACar.getOpis().isEmpty()){
            return "All fields are required!";
        }
        if(rentACar.getOpis().length()<50){
            return "Description must have at least 50 characters!";
        }

        if (findByNaziv(rentACar.getNaziv()) != null){
            return "Name already taken!";
        }
        repository.save(rentACar);
        return "true";
    }


}
