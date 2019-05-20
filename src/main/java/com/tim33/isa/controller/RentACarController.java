package com.tim33.isa.controller;

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
    RentACar update(@RequestBody RentACar noviProfil, @PathVariable long id) {
        noviProfil.setId(id);
        return service.save(noviProfil);
    }

    @GetMapping("/all")
    @ResponseBody
    List<RentACar> findAll() {
        return service.findAll();
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
    RentACar findById(@PathVariable long id) {
        return service.findById(id);
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
