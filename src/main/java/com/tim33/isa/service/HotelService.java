package com.tim33.isa.service;

import com.tim33.isa.model.Hotel;
import com.tim33.isa.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    HotelRepository repository;

    public Hotel save(Hotel noviProfil) {
        // Manipulacija profilom...

        return repository.save(noviProfil);
    }

    public Hotel update(Hotel noviProfil) {
        // Manipulacija profilom...

        return repository.save(noviProfil);
    }

    public List<Hotel> findAll() {
        return repository.findAll();
    }

    public Hotel findById(long id) {
        return repository.findById(id).orElse(null);
    }
    public void deleteById(long id) { repository.delete(findById(id)); }
    public Hotel findByNaziv(String naziv){
        return repository.findByNaziv(naziv);
    }

    public String checkHotel(Hotel hotel){

        if (hotel.getNaziv().isEmpty()||hotel.getAdresa().isEmpty()||hotel.getOpis().isEmpty()){
            return "All fields are required!";
        }
        if(hotel.getOpis().length()<50){
            return "Description must have at least 50 characters!";
        }

        if (findByNaziv(hotel.getNaziv()) != null){
            return "Name already taken!";
        }
        repository.save(hotel);
        return "true";
    }

}
