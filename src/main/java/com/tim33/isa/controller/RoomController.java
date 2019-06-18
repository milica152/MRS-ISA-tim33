package com.tim33.isa.controller;

import com.tim33.isa.dto.filter.FilterRooms;
import com.tim33.isa.model.Hotel;
import com.tim33.isa.model.RequestWrapper;
import com.tim33.isa.model.Soba;
import com.tim33.isa.service.HotelService;
import com.tim33.isa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/Rooms")
public class RoomController {

    @Autowired
    RoomService service;
    @Autowired
    HotelService hotelService;

    @PostMapping
    Soba save(@RequestBody Soba novaSoba) {
        return service.save(novaSoba);
    }

    @PutMapping("/{id}")
    Soba update(@RequestBody Soba novaSoba, @PathVariable long id) {
        novaSoba.setId(id);
        return service.save(novaSoba);
    }

    @GetMapping("/all")
    List<Soba> findAll() {
        return service.findAll();
    }

    @GetMapping("/all/{HotelId}")
    List<Soba> findHotelRooms(@PathVariable long HotelId) {
        return service.findHotelRooms(hotelService.findById(HotelId));
    }

    @GetMapping("/{id}")
    Soba findById(@PathVariable long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/addRoom/{HotelId}", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody Soba room,@PathVariable long HotelId) {
        room.setHotel(hotelService.findById(HotelId));
        room.setOcena(0);
        String mess= service.checkRoom(room);

        if (!mess.equals("true")) {
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(room, HttpStatus.OK);
        }
    }

    @RequestMapping(value="/{HotelId}/filterRooms", method = RequestMethod.POST)
    public ResponseEntity<?> filterRooms(@RequestBody FilterRooms params, @PathVariable long HotelId){
        try {
            if(params.getPriceFrom()>params.getPriceTo()){
                return new ResponseEntity<String>("Bad price range", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<List<Soba> >(service.filterRooms(params,HotelId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
