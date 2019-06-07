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
    ResponseEntity<?> update(@RequestBody Vozilo novoVozilo, @PathVariable long id) {
        if (service.findById(id) != null) {
            novoVozilo.setId(id);
            return new ResponseEntity<>(service.save(novoVozilo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no Car with that ID", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    List<Vozilo> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable long id) {
        Vozilo car = service.findById(id);
        if (car != null) {
            return new ResponseEntity<>(car, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no Car with that ID", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/fromRC/{idRentACara}")
    ResponseEntity<List<Vozilo>> findAllFromRC(@PathVariable long idRentACara) {
        return new ResponseEntity<>(service.findAllFromRC(idRentACara), HttpStatus.OK);
    }

    @GetMapping("/fromRCWithFilters/{idRentACara}")
    ResponseEntity<List<Vozilo>> findAllWithFilter(@PathVariable long idRentACara, @RequestBody FilterPretrageVozila filter) {
        return new ResponseEntity<>(service.findAllWithFilter(idRentACara, filter), HttpStatus.OK);
    }
}
