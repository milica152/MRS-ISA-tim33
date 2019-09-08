package com.tim33.isa.controller;


import com.tim33.isa.model.Avion;
import com.tim33.isa.service.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Airplane")
public class AirplaneController {
    @Autowired
    AirplaneService service;


    @PostMapping
    @ResponseBody
    Avion save(@RequestBody Avion newSeat) {
        return service.save(newSeat);
    }

    @PutMapping("/{id}")
    @ResponseBody
    Avion update(@RequestBody Avion newAirplane, @PathVariable long id) {
        //newAirplane.setId(id);   ??
        return service.save(newAirplane);
    }

    @GetMapping("/all")
    @ResponseBody
    List<Avion> findAll() {
        return service.findAll();
    }

    @GetMapping("/specific/{id}")
    @ResponseBody
    Avion findById(@PathVariable long id) {
        return service.findById(id);
    }


    @RequestMapping(value = "deleteAirplane/{idDel}", method = RequestMethod.POST)
    @ResponseBody
    public void deleteSeat(@PathVariable Long idDel){service.deleteById(idDel);}


}
