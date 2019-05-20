package com.tim33.isa.controller;


import com.tim33.isa.model.Hotel;
import com.tim33.isa.model.RequestWrapper;
import com.tim33.isa.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/Hotels")

public class HotelController {


    @Autowired
    HotelService service;

    @PostMapping
    @ResponseBody
    Hotel save(@RequestBody Hotel noviProfil) {
        return service.save(noviProfil);
    }

    @PutMapping("/{id}")
    @ResponseBody
    Hotel update(@RequestBody Hotel noviProfil, @PathVariable long id) {
        noviProfil.setId(id);
        return service.save(noviProfil);
    }

    @GetMapping("/all")
    @ResponseBody
    List<Hotel> findAll() {
        return service.findAll();
    }

    @GetMapping
    public String welcome() {
        return "hotel";
    }

    @GetMapping("/{id}")
    @ResponseBody
    Hotel findById(@PathVariable long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "deleteHotel/{idDel}", method = RequestMethod.POST)
    @ResponseBody
    public void deleteHotel(@PathVariable Long idDel){service.deleteById(idDel);}

    @RequestMapping(value = "/addHotel", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody Hotel hotel) {
        hotel.setAdmins(Collections.emptySet());
        hotel.setKonfiguracija_soba(Collections.emptySet());
        hotel.setOcena(0);
        hotel.setUsluge(Collections.emptySet());
        hotel.setCenaOd(0);

        String mess= service.checkHotel(hotel);

        if (!mess.equals("true")) {
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(hotel, HttpStatus.OK);
        }
    }
}
