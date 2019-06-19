package com.tim33.isa.controller;

import com.tim33.isa.model.LokacijaPresedanja;
import com.tim33.isa.service.LokacijaPresedanjaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/LP")
public class LokacijaPresedanjaController {

    @Autowired
    LokacijaPresedanjaService service;

    @PostMapping
    @ResponseBody
    LokacijaPresedanja save(@RequestBody LokacijaPresedanja novaLP) {
        return service.save(novaLP);
    }

    @PutMapping("/{id}")
    @ResponseBody
    LokacijaPresedanja update(@RequestBody LokacijaPresedanja novaLP, @PathVariable long id) {
        return service.save(novaLP);
    }

    @GetMapping("/all")
    @ResponseBody
    List<LokacijaPresedanja> findAll() {
        return service.findAll();
    }

    @GetMapping("/specific/{id}")
    @ResponseBody
    LokacijaPresedanja findById(@PathVariable long id) {
        return service.findById(id);
    }


}
