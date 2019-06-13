package com.tim33.isa.controller;

import com.tim33.isa.dto.filter.SearchFlight;
import com.tim33.isa.model.Let;
import com.tim33.isa.service.LetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;


@Controller
@RequestMapping("/Flight")
public class LetController {
    @Autowired
    LetService service;

    @PostMapping
    @ResponseBody
    Let save(@RequestBody Let novoVozilo) {
        return service.save(novoVozilo);
    }

    @PutMapping("/{id}")
    @ResponseBody
    Let update(@RequestBody Let noviLet, @PathVariable long id) {
        noviLet.setId(id);
        return service.save(noviLet);
    }

    @GetMapping("/all")
    @ResponseBody
    List<Let> findAll() {
        return service.findAll();
    }

    @GetMapping("/specific/{id}")
    @ResponseBody
    Let findById(@PathVariable long id) {
        return service.findById(id);
    }


    @RequestMapping(value = "deleteLet/{idDel}", method = RequestMethod.POST)
    @ResponseBody
    public void deleteFlight(@PathVariable Long idDel){service.deleteById(idDel);}

    @GetMapping("/fromAviocompany/{idAviocomp}")
    ResponseEntity<List<Let>> findAllFromAviocompany(@PathVariable long idAviocomp) {
        return new ResponseEntity<>(service.findAllFromAviocompany(idAviocomp), HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity<List<Let>> searchFlight(@Valid @RequestBody SearchFlight criteria) {
        System.out.println("dosao na server");
            return new ResponseEntity<>(service.searchFlight(criteria), HttpStatus.OK);
    }

}
