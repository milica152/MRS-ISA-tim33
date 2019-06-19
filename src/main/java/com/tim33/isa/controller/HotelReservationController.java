package com.tim33.isa.controller;
import com.tim33.isa.model.HotelReservation;
import com.tim33.isa.model.Soba;
import com.tim33.isa.service.HotelReservationService;
import com.tim33.isa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("/HotelReservation")
public class HotelReservationController {

    @Autowired
    HotelReservationService service;
    @Autowired
    RoomService roomService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addHotelReservation() {
        HotelReservation reservation = new HotelReservation();
        service.save(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }
    @RequestMapping(value = "/addRoom/{ReservationId}", method = RequestMethod.POST)
    public ResponseEntity<?> addRoomToReservation(@RequestBody Soba room, @PathVariable Long ReservationId) {
        HotelReservation reservation = service.findById(ReservationId);
        reservation.getRoom().add(room);
        double price = 0;
        for(Soba s:reservation.getRoom()){
            price = price + s.getCena_nocenja();
        }
        reservation.setPrice(price);
        service.save(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }

    @RequestMapping(value = "/deleteRoom/{ReservationId}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteRoomFromReservation(@RequestBody Soba room, @PathVariable Long ReservationId) {
        HotelReservation reservation = service.findById(ReservationId);
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
        reservation.setPrice(price);
        service.save(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }

    @RequestMapping(value = "/deleteRoomCart/{RoomId}/{ReservationId}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteRoomFromReservation(@PathVariable Long ReservationId, @PathVariable Long RoomId) {
        double price = 0;
        HotelReservation reservation = service.findById(ReservationId);
        Soba room = roomService.findById(RoomId);

        for(Soba s: reservation.getRoom()){
            if(s.getId()==room.getId()){
                reservation.getRoom().remove(s);
                break;
            }
        }
        for(Soba s:reservation.getRoom()){
            price = price + s.getCena_nocenja();
        }
        reservation.setPrice(price);
        service.save(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }
}
