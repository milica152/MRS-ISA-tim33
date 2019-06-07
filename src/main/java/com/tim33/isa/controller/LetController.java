package com.tim33.isa.controller;

import com.tim33.isa.model.Let;
import com.tim33.isa.service.LetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/Flight")
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

    @GetMapping("/all")
    List<Let> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    Let findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/fromAviocompany/{idAviocomp}")
    ResponseEntity<List<Let>> findAllFromAviocompany(@PathVariable long idAviocomp) {
        return new ResponseEntity<>(service.findAllFromAviocompany(idAviocomp), HttpStatus.OK);
    }
}
