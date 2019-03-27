package com.tim33.isa.controller;


import com.tim33.isa.model.Hotel;
import com.tim33.isa.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/HotelProfil")

public class HotelController {


    @Autowired
    HotelService service;

    @PostMapping
    Hotel save(@RequestBody Hotel noviProfil) {
        return service.save(noviProfil);
    }

    @PutMapping("/{id}")
    Hotel update(@RequestBody Hotel noviProfil, @PathVariable long id) {
        noviProfil.setId(id);
        return service.save(noviProfil);
    }

    @GetMapping
    List<Hotel> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    Hotel findById(@PathVariable long id) {
        return service.findById(id);
    }
}
