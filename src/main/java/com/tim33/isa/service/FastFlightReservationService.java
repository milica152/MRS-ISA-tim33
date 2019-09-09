package com.tim33.isa.service;

import com.tim33.isa.model.FastFlightReservation;
import com.tim33.isa.model.FlightReservation;
import com.tim33.isa.model.Sediste;
import com.tim33.isa.repository.FastFlightReservationRepository;
import com.tim33.isa.repository.FlightReservationRepository;
import com.tim33.isa.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FastFlightReservationService {
    @Autowired
    FastFlightReservationRepository repository;
    @Autowired
    FlightReservationRepository frRepository;
    @Autowired
    SeatService seatService;


    public void deleteById(Long id) { repository.deleteById((long)id); }


    public FastFlightReservation save(FastFlightReservation newRes){
        // Manipulacija profilom...
        return repository.save(newRes);
    }

    public FastFlightReservation update(FastFlightReservation newRes){
        // Manipulacija profilom...
        return repository.save(newRes);
    }

    public List<FastFlightReservation> findAll(){
        return repository.findAll();
    }

    public FastFlightReservation findById(long id){
        return repository.findById(id);

    }

    public List<FastFlightReservation> findAllByFlightId(long id) {
        return repository.findAllByFlightId(id);
    }

    public String reserve(FastFlightReservation ffr) {
        //napravi rezervaciju na osnovu brze rez sa ovim idom
        try{
            FlightReservation fr = new FlightReservation();
            Sediste s = seatService.findById(ffr.getSeat().getId());
            s.setReserved(true);
            seatService.update(s);
            fr.setName(ffr.getName());
            fr.setSurname(ffr.getSurname());
            fr.setPassportNum(ffr.getPassportNum());
            fr.setTime(new Date());
            fr.setFlight(ffr.getFlight());
            fr.setSeat(ffr.getSeat());
            fr.setPrice(ffr.getPrice() - ffr.getPrice()*ffr.getDiscount());

            frRepository.save(fr);
            repository.deleteById(ffr.getId());
        }catch (Exception ex){
            return "Somethig's wrong..";
        }

        return "true";
    }
}
