package com.tim33.isa.service;

import com.tim33.isa.model.HotelReservation;
import com.tim33.isa.model.Soba;
import com.tim33.isa.model.UslugeHotela;
import com.tim33.isa.repository.HotelReservationRepository;
import com.tim33.isa.repository.HotelServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelReservationService {

    @Autowired
    HotelReservationRepository repository;
    @Autowired
    HotelServicesRepository serviceRepository;

    public HotelReservation save(HotelReservation novaRezervacija) {
        return repository.save(novaRezervacija);
    }

    public HotelReservation update(HotelReservation novaRezervacija) {
        return repository.save(novaRezervacija);
    }

    public List<HotelReservation> findAll() {
        return repository.findAll();
    }

    public HotelReservation findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(Long id) { repository.delete(findById(id)); }

    public HotelReservation addRoomToReservation(Soba room, HotelReservation reservation){
        reservation.getRoom().add(room);
        double price = 0;
        for(Soba s:reservation.getRoom()){
            price = price + s.getCena_nocenja();
        }
        double servicePrice=0;
        if(reservation.getHotelServices().size()==0){
            reservation.setPrice(price);
        }else{
            for(UslugeHotela uh:reservation.getHotelServices()){
                servicePrice = servicePrice + uh.getCena();
            }
            if(reservation.getRoom().size()!=0){
                int roomNo = reservation.getRoom().size();
                price = price + servicePrice*roomNo;
            }
            reservation.setPrice(price);
        }

        save(reservation);
        return reservation;
    }
    public HotelReservation deleteRoomFromReservation(Soba room, Long reservationId){
        HotelReservation reservation = findById(reservationId);
        double price = 0;
        for(Soba s: reservation.getRoom()){
            if(s.getId()==room.getId()){
                reservation.getRoom().remove(s);
                break;
            }
        }
        for(Soba s:reservation.getRoom()){
            price = price + s.getCena_nocenja();
        }
        double servicePrice=0;
        if(reservation.getHotelServices().size()==0){
            reservation.setPrice(price);
        }else{
            for(UslugeHotela uh:reservation.getHotelServices()){
                servicePrice = servicePrice + uh.getCena();
            }
            int roomNo = reservation.getRoom().size();
            price = price + servicePrice*roomNo;
            reservation.setPrice(price);
        }
        save(reservation);
        return reservation;
    }

    public HotelReservation addServicesToReservation(String[] services, Long reservationId){
        HotelReservation reservation = findById(reservationId);
        reservation.getHotelServices().clear();
        int noOfRooms = 0;
        double price = 0;
        double discPerc = 0;
        if(reservation.getRoom().size()==0){
            return null;
        }else{
            noOfRooms = reservation.getRoom().size();
            for(Soba r: reservation.getRoom()){
                price = price + r.getCena_nocenja();
            }
        }
        if(services==null){
            reservation.setPrice(price);
            reservation.setDiscount(discPerc);
            save(reservation);
            return reservation;
        }

        if(services.length==3||services.length==4){
            discPerc = 0.05;
        }else if(services.length==5 || services.length==6){
            discPerc = 0.1;
        }else if(services.length==7 || services.length==8){
            discPerc = 0.2;
        }
        reservation.setDiscount(discPerc);

        for(String s:services){
            UslugeHotela service = serviceRepository.findByNazivAndHotel(s, reservation.getHotelId());
            price = price + (service.getCena())*noOfRooms;
            reservation.getHotelServices().add(service);
        }
        price = price - price*discPerc;
        reservation.setPrice(price);
        save(reservation);
        return reservation;
    }



}
