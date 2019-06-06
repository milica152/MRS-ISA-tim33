package com.tim33.isa.service;

import com.tim33.isa.model.Hotel;
import com.tim33.isa.model.Soba;
import com.tim33.isa.model.TipSobe;
import com.tim33.isa.model.UslugeHotela;
import com.tim33.isa.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service

public class RoomService {

    @Autowired
    RoomRepository repository;
    //@Autowired
    //UslugeHotelaService uslugeService;


    public Soba save(Soba newRoom) {
        return repository.save(newRoom);
    }

    public Soba update(Soba newRoom) {
        return repository.save(newRoom);
    }

    public List<Soba> findAll() {
        return repository.findAll();
    }

    public Soba findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public String checkRoom(Soba room){

        if (room.getCena_nocenja()<1){
            return "Prize per night has to be positive number!";
        }else if(room.getHotel()==null){
            return "Room must belong to hotel!";
        }else if(room.getOcena()!=0){
            return "Initial rating must be 0!";
        }

        repository.save(room);
        /*for(UslugeHotela u:room.getUsluga()){
            u.setRoom(room);
            destser.save(d);

        }*/
        return "true";
    }

    /*public static boolean contains(String test) {

        for (TipSobe c : TipSobe.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }

        return false;
    }*/

    public boolean IsRoomReserved(Soba room, Date fromDate, Date toDate) {
        if(fromDate == null || toDate == null) {
            return false;
        }
        /*for(RezervacijaSobe r : this.rezervacijeRepository.findAllByRezervisanaSoba(soba)) {
            if(!pocetniDatum.after(r.getDatumOdlaska()) && !r.getDatumDolaska().after(krajnjiDatum)) {
                return true;
            }
        }*/
        return false;
    }
}
