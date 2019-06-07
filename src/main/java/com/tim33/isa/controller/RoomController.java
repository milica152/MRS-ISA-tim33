package com.tim33.isa.controller;

import com.tim33.isa.model.RequestWrapper;
import com.tim33.isa.model.Soba;
import com.tim33.isa.service.HotelService;
import com.tim33.isa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    @GetMapping("/{id}")
    Soba findById(@PathVariable long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/addRoom/{HotelId}", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody Soba room,@PathVariable long HotelId) {
        room.setHotel(hotelService.findById(HotelId));
        room.setUsluga(Collections.emptySet());

        String mess= service.checkRoom(room);

        if (!mess.equals("true")) {
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(room, HttpStatus.OK);
        }
    }

}
