package com.tim33.isa.controller;

import com.tim33.isa.model.RentACar;
import com.tim33.isa.service.RentACarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String welcome() {
        return "rentacar";
    }

    @GetMapping("/{id}")
    @ResponseBody
    RentACar findById(@PathVariable long id) {
        return service.findById(id);
    }
}
