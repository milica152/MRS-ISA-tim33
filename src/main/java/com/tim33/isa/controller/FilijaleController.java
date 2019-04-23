package com.tim33.isa.controller;

import com.tim33.isa.model.Filijala;
import com.tim33.isa.service.FilijaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Filijale")
public class FilijaleController {
    
    @Autowired
    FilijaleService service;

    @PostMapping
    Filijala save(@RequestBody Filijala novaFilijala) {
        return service.save(novaFilijala);
    }

    @PutMapping("/{id}")
    Filijala update(@RequestBody Filijala novaFilijala, @PathVariable long id) {
        novaFilijala.setId(id);
        return service.save(novaFilijala);
    }

    @GetMapping("/all")
    List<Filijala> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    Filijala findById(@PathVariable long id) {
        return service.findById(id);
    }

}
