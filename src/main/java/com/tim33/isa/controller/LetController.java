package com.tim33.isa.controller;

import com.tim33.isa.model.Let;
import com.tim33.isa.service.LetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class LetController {
    @Autowired
    LetService service;

    @PostMapping
    Let save(@RequestBody Let novoVozilo) {
        return service.save(novoVozilo);
    }

    @PutMapping("/{id}")
    Let update(@RequestBody Let noviLet, @PathVariable long id) {
        noviLet.setId(id);
        return service.save(noviLet);
    }

    @GetMapping
    List<Let> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    Let findById(@PathVariable long id) {
        return service.findById(id);
    }
}
