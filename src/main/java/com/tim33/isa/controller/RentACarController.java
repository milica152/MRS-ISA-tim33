package com.tim33.isa.controller;

import com.tim33.isa.model.RentACar;
import com.tim33.isa.service.RentACarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/RCProfil")
public class RentACarController {

    @Autowired
    RentACarService service;

    @PostMapping
    RentACar save(@RequestBody RentACar noviProfil) {
        return service.save(noviProfil);
    }

    @PutMapping("/{id}")
    RentACar update(@RequestBody RentACar noviProfil, @PathVariable long id) {
        noviProfil.setId(id);
        return service.save(noviProfil);
    }

    @GetMapping
    List<RentACar> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    RentACar findById(@PathVariable long id) {
        return service.findById(id);
    }

}
