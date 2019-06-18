package com.tim33.isa.service;

import com.tim33.isa.dto.filter.SearchHotel;
import com.tim33.isa.model.Hotel;
import com.tim33.isa.model.Soba;
import com.tim33.isa.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    HotelRepository repository;
    @Autowired
    RoomService roomService;

    public Hotel save(Hotel noviProfil) {
        return repository.save(noviProfil);
    }

    public Hotel update(Hotel noviProfil) {
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

    public List<Hotel> searchHotel(SearchHotel criteria) throws ParseException{
        Date fromDate = null;
        Date toDate = null;
        SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
        try {
            if (!criteria.getDateFrom().equals("") && criteria.getDateFrom() != null) {
                fromDate = df.parse(criteria.getDateFrom());
            }

            if (!criteria.getDateTo().equals("") && criteria.getDateTo() != null) {
                toDate = df.parse(criteria.getDateTo());
            }
        } catch (ParseException e) {
            System.out.println("Nevalidan format datuma.");
        }

        List<Hotel> result = repository.findAll();

        Iterator<Hotel> it = result.iterator();
        Hotel current;

        while (it.hasNext()) {
            current = it.next();
            if (!current.getNaziv().toUpperCase()
                    .contains(criteria.getNameOrDestination().toUpperCase())
                    && !current.getAdresa().toUpperCase()
                    .contains(criteria.getNameOrDestination().toUpperCase())) {
                it.remove();
            }
        }

        it = result.iterator();
        boolean removeHotel;

        // proveriti svaki hotel
        while (it.hasNext()) {
            current = it.next();
            removeHotel = true;
            for (Soba room : current.getKonfiguracija_soba()) {
                if (!roomService.IsRoomReserved(room, fromDate, toDate)) {
                    removeHotel=false;
                }

            }
            if(removeHotel && current.getKonfiguracija_soba().size()>0){
                result.remove(current);
            }

        }

        return result;
    }
    public void updateHotelPrice(Hotel hotel){
        double cena_min = Double.POSITIVE_INFINITY;
        if(hotel.getKonfiguracija_soba().isEmpty()){
            cena_min=0;
        }
        for (Soba s:hotel.getKonfiguracija_soba()) {
            if(s.getCena_nocenja()<cena_min){
                cena_min=s.getCena_nocenja();
            }
        }
        hotel.setCenaOd(cena_min);
        save(hotel);

    }

}
