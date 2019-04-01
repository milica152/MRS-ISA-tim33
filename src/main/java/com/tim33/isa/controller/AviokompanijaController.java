package com.tim33.isa.controller;

import com.tim33.isa.model.Aviokompanija;
import com.tim33.isa.service.AviokompanijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/AviokompanijaProfil")
public class AviokompanijaController {
    @Autowired
    AviokompanijaService service;

    @PostMapping
    Aviokompanija save(@RequestBody Aviokompanija noviProfil) {
        return service.save(noviProfil);
    }

    @PutMapping("/{id}")
    Aviokompanija update(@RequestBody Aviokompanija noviProfil, @PathVariable long id) {
        noviProfil.setId(id);
        return service.save(noviProfil);
    }

    @GetMapping
    List<Aviokompanija> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    Aviokompanija findById(@PathVariable long id) {
        return service.findById(id);
    }


}
