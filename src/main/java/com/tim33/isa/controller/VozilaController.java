package com.tim33.isa.controller;

import com.tim33.isa.dto.filter.FilterPretrageVozila;
import com.tim33.isa.model.Vozilo;
import com.tim33.isa.service.VozilaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Vozila")
public class VozilaController {
    
    @Autowired
    VozilaService service;

    @PostMapping
    Vozilo save(@RequestBody Vozilo novoVozilo) {
        return service.save(novoVozilo);
    }

    @PutMapping("/{id}")
    Vozilo update(@RequestBody Vozilo novoVozilo, @PathVariable long id) {
        novoVozilo.setId(id);
        return service.save(novoVozilo);
    }

    @GetMapping
    List<Vozilo> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    Vozilo findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/filter")
    ResponseEntity<List<Vozilo>> findAllWithFilter(long idRentACara, FilterPretrageVozila filter) {
        return new ResponseEntity<>(service.findAllWithFilter(idRentACara, filter), HttpStatus.OK);
    }

}
