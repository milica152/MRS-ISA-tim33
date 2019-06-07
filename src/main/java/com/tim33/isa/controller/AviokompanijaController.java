
package com.tim33.isa.controller;

import com.tim33.isa.model.Aviokompanija;
import com.tim33.isa.model.LetZaDodavanje;
import com.tim33.isa.service.AviokompanijaService;
import com.tim33.isa.service.LetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/Aviocompany")
public class AviokompanijaController {
    @Autowired
    AviokompanijaService service;

    @Autowired
    LetService letService;

    @RequestMapping(value = "addAviocompany", method = RequestMethod.POST)
    @ResponseBody
    Aviokompanija save(@RequestBody Aviokompanija noviProfil) {
        return service.save(noviProfil);
    }

    @PutMapping("/{id}")
    @ResponseBody
    Aviokompanija update(@RequestBody Aviokompanija noviProfil, @PathVariable long id) {
        noviProfil.setId(id);
        return service.save(noviProfil);
    }

    @PostMapping("/{id}/addFlight")
    @ResponseBody
    public ResponseEntity<?> addFlight(@Valid @RequestBody LetZaDodavanje noviLet, @PathVariable String id) {

        String mess= letService.checkAdding(noviLet, id);
        if (!mess.equals("true")) {
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
    }


    @GetMapping("/all")
    @ResponseBody
    List<Aviokompanija> findAll() {
        return service.findAll();
    }

    @GetMapping
    public String aviocompanyServis() {
        return "aviokompanije";
    }

    @GetMapping("/{id}")
    public String aviocompanyProfile() {
        return "aviokompanijaprofil";
    }

    @GetMapping("/{id}/noviLet1")
    public String aviocompanyNewFlight(@PathVariable String id) {
        return "noviLet";
    }

    @GetMapping("/specific/{id}")
    @ResponseBody
    Aviokompanija findById(@PathVariable long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "deleteAviocompany/{idDel}", method = RequestMethod.POST)   //ako ajax posalje post metodom na ovu adr.
    @ResponseBody
    public void deleteAviocompany(@PathVariable Long idDel){service.deleteById(idDel);}    //izbrisi dati hotel sa servisa

}
