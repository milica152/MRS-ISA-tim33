package com.tim33.isa.controller;

import com.tim33.isa.dto.filter.FilterPretrageRCServisa;
import com.tim33.isa.model.RentACar;
import com.tim33.isa.service.RentACarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/RentACar")
public class RentACarController {

    @Autowired
    RentACarService service;

    @PostMapping
    @ResponseBody
    RentACar save(@RequestBody RentACar noviProfil) {
        return service.save(noviProfil);
    }

    @PutMapping("/{id}")
    @ResponseBody
    ResponseEntity<?> update(@RequestBody RentACar noviProfil, @PathVariable long id) {
        if (service.findById(id) != null) {
            noviProfil.setId(id);
            return new ResponseEntity<>(service.save(noviProfil), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no Rent a Car service with that ID", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/all")
//    @ResponseBody
    ResponseEntity<List<RentACar>> findAll(@RequestBody FilterPretrageRCServisa filter) {
        return new ResponseEntity<>(service.findAllWithFilter(filter), HttpStatus.OK);
    }

    @GetMapping
    public String rcServicesPage() {
        return "rentacar";
    }

    @GetMapping("/{id}")
    public String rcProfilePage() {
        return "rcprofil";
    }

    @GetMapping("/specific/{id}")
    @ResponseBody
    ResponseEntity<?> findById(@PathVariable long id) {
        RentACar rc = service.findById(id);
        if (rc != null) {
            return new ResponseEntity<>(rc, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no Rent a Car service with that ID", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/addRCS", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody RentACar rentACar) {
        rentACar.setAdmins(Collections.emptySet());
        rentACar.setFilijale(Collections.emptySet());
        rentACar.setVozila(Collections.emptySet());

        String mess= service.checkRCS(rentACar);

        if (!mess.equals("true")) {
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(rentACar, HttpStatus.OK);
        }
    }
}
