package com.tim33.isa.controller;


import com.tim33.isa.model.Hotel;
import com.tim33.isa.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/Hotels")

public class HotelController {


    @Autowired
    HotelService service;

    @PostMapping
    @ResponseBody
    Hotel save(@RequestBody Hotel noviProfil) {
        return service.save(noviProfil);
    }

    @PutMapping("/{id}")
    @ResponseBody
    Hotel update(@RequestBody Hotel noviProfil, @PathVariable long id) {
        noviProfil.setId(id);
        return service.save(noviProfil);
    }

    @GetMapping("/all")
    @ResponseBody
    List<Hotel> findAll() {
        return service.findAll();
    }

    @GetMapping
    public String welcome() {
        return "hotel";
    }

    @GetMapping("/{id}")
    @ResponseBody
    Hotel findById(@PathVariable long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "deleteHotel/{idDel}", method = RequestMethod.POST)
    @ResponseBody
    public void deleteHotel(@PathVariable Long idDel){service.deleteById(idDel);}
}
