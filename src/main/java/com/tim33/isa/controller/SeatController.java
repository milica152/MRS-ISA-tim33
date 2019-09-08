package com.tim33.isa.controller;

import com.tim33.isa.model.Sediste;
import com.tim33.isa.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Seats")
public class SeatController {
    @Autowired
    SeatService service;

    @PostMapping
    @ResponseBody
    Sediste save(@RequestBody Sediste newSeat) {
        return service.save(newSeat);
    }

    @PutMapping("/{id}")
    @ResponseBody
    Sediste update(@RequestBody Sediste newSeat, @PathVariable long id) {
        newSeat.setId(id);
        return service.save(newSeat);
    }

    @GetMapping("/all")
    @ResponseBody
    List<Sediste> findAll() {
        return service.findAll();
    }

    @GetMapping("/specific/{id}")
    @ResponseBody
    Sediste findById(@PathVariable long id) {
        return service.findById(id);
    }


    @RequestMapping(value = "deleteSeat/{idDel}", method = RequestMethod.POST)
    @ResponseBody
    public void deleteSeat(@PathVariable Long idDel){service.deleteById(idDel);}


    @GetMapping("/fromPlane/{idFlight}")
    ResponseEntity<Sediste[][]> findAllFromPlane(@PathVariable long idFlight) {
        return new ResponseEntity<Sediste[][]>(service.findAllFromPlane(idFlight), HttpStatus.OK);
    }


}
