package com.tim33.isa.controller;

import com.tim33.isa.model.*;
import com.tim33.isa.service.AviokompanijaService;
import com.tim33.isa.service.DestinacijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Console;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/AviokompanijaProfil")
public class AviokompanijaController {
    @Autowired
    AviokompanijaService service;
    @Autowired
    DestinacijaService serviceDest;

    @PostMapping
    Aviokompanija save(@RequestBody Aviokompanija noviProfil) {
        return service.save(noviProfil);
    }

    @PutMapping("/{id}")
    Aviokompanija update(@RequestBody Aviokompanija noviProfil, @PathVariable long id) {
        noviProfil.setId(id);
        return service.save(noviProfil);
    }

    @GetMapping("/all")
    @ResponseBody
    List<Aviokompanija> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    Aviokompanija findById(@PathVariable long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/addAirline", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody RequestWrapper rw) {
        Aviokompanija airline = rw.getAirline();
        Set<Destinacija> destinations = rw.getDestinations();
        airline.setDestinations(destinations);
        airline.setKarteZaBrzu(Collections.emptySet());
        airline.setAdmins(Collections.emptySet());
        String mess= service.checkAK(airline);

        if (!mess.equals("true")) {
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(airline, HttpStatus.OK);
        }
    }


}
