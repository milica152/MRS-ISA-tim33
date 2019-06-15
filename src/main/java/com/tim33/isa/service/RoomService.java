package com.tim33.isa.service;

import com.tim33.isa.dto.filter.FilterRooms;
import com.tim33.isa.model.Hotel;
import com.tim33.isa.model.Soba;
import com.tim33.isa.model.TipSobe;
import com.tim33.isa.model.UslugeHotela;
import com.tim33.isa.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service

public class RoomService {

    @Autowired
    RoomRepository repository;
    @Autowired
    HotelService hotelService;

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

    public List<Soba> findHotelRooms(Hotel hotel){return repository.findHotelRooms(hotel);}

    public String checkRoom(Soba room){


        if (room.getCena_nocenja()<1 ){
            return "Prize per night has to be positive number!";
        }else if(room.getHotel()==null){
            return "Room must belong to hotel!";
        }else if(room.getOcena()!=0){
            System.out.println(repository.findHotelRoomByNo(room.getRoomNo(), room.getHotel()));
            return "Initial rating must be 0!";
        }else if(repository.findHotelRoomByNo(room.getRoomNo(), room.getHotel())!=null){
            return "Room Number already taken!";
        }else if(room.getRoomFloor()<0){
            return "Floor starts from 0!";
        }

        repository.save(room);
        hotelService.updateHotelPrice(room.getHotel());
        /*for(UslugeHotela u:room.getUsluga()){
            u.setRoom(room);
            destser.save(d);

        }*/
        return "true";
    }

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

    public Integer getNoOfRoomTYpe(Integer type, Hotel hotel){
        return repository.noOfRoomType(type, hotel);
    }

    public List<Soba> findRoomsByTypePrice(Integer type, Hotel hotel, double priceFrom, double priceTo){
        return repository.findRoomsByTypePrice(type, hotel, priceFrom, priceTo);
    }

    public List<Soba> filterRooms(FilterRooms params, long HotelId){
        List<String> listRoomTypes = params.getRoomTypeList();
        double priceFrom = params.getPriceFrom();
        double priceTo = params.getPriceTo();
        Hotel hotel = hotelService.findById(HotelId);
        if (priceTo==0 && priceFrom==0){
            priceTo=99999999999999999.99;
        }

        int single = Collections.frequency(listRoomTypes, "JEDNOKREVETNA");
        int two = Collections.frequency(listRoomTypes, "DVOKREVETNA");
        int three = Collections.frequency(listRoomTypes, "TROKREVETNA");
        int four = Collections.frequency(listRoomTypes, "CETVOROKREVETNA");
        int five = Collections.frequency(listRoomTypes, "PETOKREVETNA");
        int six = Collections.frequency(listRoomTypes, "SESTOKREVETNA");

        int singleHotel = getNoOfRoomTYpe(0, hotel);
        int twoHotel = getNoOfRoomTYpe(1, hotel);
        int threeHotel = getNoOfRoomTYpe(2, hotel);
        int fourHotel = getNoOfRoomTYpe(3, hotel);
        int fiveHotel = getNoOfRoomTYpe(4, hotel);
        int sixHotel = getNoOfRoomTYpe(5, hotel);
        if(single>singleHotel||two>twoHotel||three>threeHotel||four>fourHotel||five>fiveHotel||six>sixHotel){
            return new ArrayList<>();
        }

        List<Soba> filteredRooms = new ArrayList<>();
        if(single>0){
            filteredRooms.addAll(findRoomsByTypePrice(0,hotel, priceFrom, priceTo));
        }
        if(two>0){
            filteredRooms.addAll(findRoomsByTypePrice(1,hotel, priceFrom, priceTo));
        }
        if(three>0){
            filteredRooms.addAll(findRoomsByTypePrice(2,hotel, priceFrom, priceTo));
        }
        if(four>0){
            filteredRooms.addAll(findRoomsByTypePrice(3,hotel, priceFrom, priceTo));
        }
        if(five>0){
            filteredRooms.addAll(findRoomsByTypePrice(4,hotel, priceFrom, priceTo));
        }
        if(six>0){
            filteredRooms.addAll(findRoomsByTypePrice(5,hotel, priceFrom, priceTo));
        }
        return filteredRooms;
    }
}
