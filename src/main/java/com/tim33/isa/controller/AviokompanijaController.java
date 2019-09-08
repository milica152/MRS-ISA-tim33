
package com.tim33.isa.controller;

import com.tim33.isa.dto.filter.SearchAirline;
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
import java.util.ArrayList;
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

        String mess= letService.checkAdding(noviLet);
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
        return "pretragaletova";
    }

    @GetMapping("/{id}/fastReservations")
    public String fastReservations(@PathVariable String id) {
        return "fastreservations";
    }


    @GetMapping("/{id}")
    public String aviocompanyProfile(@PathVariable long id) {
        return "aviokompanijaprofil";
    }

    @GetMapping("/{id}/reservation/{flightId}")
    public String flightReservation(@PathVariable long id,@PathVariable String flightId){return "seatReservation";}

    @GetMapping("/specific/{id}")
    @ResponseBody
    Aviokompanija findById(@PathVariable long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "deleteAviocompany/{idDel}", method = RequestMethod.POST)   //ako ajax posalje post metodom na ovu adr.
    @ResponseBody
    public void deleteAviocompany(@PathVariable Long idDel){service.deleteById(idDel);}

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<Aviokompanija>> search(@Valid @RequestBody SearchAirline params){return new ResponseEntity<>(service.searchAK(params), HttpStatus.OK);}

}
