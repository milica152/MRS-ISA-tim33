package com.tim33.isa.controller;

import com.tim33.isa.model.FlightReservation;
import com.tim33.isa.service.FlightReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/FlightReservationController")
public class FlightReservationController {
    @Autowired
    FlightReservationService service;

    @PostMapping
    @ResponseBody
    FlightReservation save(@RequestBody FlightReservation novoVozilo) {
        return service.save(novoVozilo);
    }

    @PutMapping("/{id}")
    @ResponseBody
    FlightReservation update(@RequestBody FlightReservation noviLet, @PathVariable long id) {
        noviLet.setId(id);
        return service.save(noviLet);
    }

    @GetMapping("/all")
    @ResponseBody
    List<FlightReservation> findAll() {
        return service.findAll();
    }

    @GetMapping("/specific/{id}")
    @ResponseBody
    FlightReservation findById(@PathVariable long id) {
        return service.findById(id);
    }


    @GetMapping("/fromFlight/{id}")
    ResponseEntity<List<FlightReservation>> findAllFromFlight(@PathVariable long idFlight) {
        return new ResponseEntity<>(service.findAllFromFlight(idFlight), HttpStatus.OK);
    }

}
