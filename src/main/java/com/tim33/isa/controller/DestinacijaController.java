package com.tim33.isa.controller;

import com.tim33.isa.model.Destinacija;
import com.tim33.isa.service.DestinacijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Destinacija")
public class DestinacijaController {

    @Autowired
    DestinacijaService service;

    @PostMapping
    @ResponseBody
    Destinacija save(@RequestBody Destinacija novaDestinacija) {
        return service.save(novaDestinacija);
    }

    @PutMapping("/{id}")
    @ResponseBody
    Destinacija update(@RequestBody Destinacija novaDestinacija, @PathVariable long id) {
        novaDestinacija.setId(id);
        return service.save(novaDestinacija);
    }

    @GetMapping("/all")
    @ResponseBody
    List<Destinacija> findAll() {
        return service.findAll();
    }

    @GetMapping("/{name}")
    @ResponseBody
    Destinacija findByGrad(@PathVariable String name) {
        return service.findByGrad(name);
    }



}
