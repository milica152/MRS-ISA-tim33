package com.tim33.isa.controller;

import com.tim33.isa.model.RequestHotelServices;
import com.tim33.isa.model.UslugeHotela;
import com.tim33.isa.service.HotelServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/HotelServices")
public class HotelServicesController {

    @Autowired
    HotelServicesService service;

    @PostMapping
    @ResponseBody
    UslugeHotela save(@RequestBody UslugeHotela novaUsluga) {
        return service.save(novaUsluga);
    }

    @RequestMapping(value = "/add/{HotelId}", method = RequestMethod.POST)
    public ResponseEntity<?> addServicesToHotel(@RequestBody RequestHotelServices request, @PathVariable long HotelId) {
        service.addServicesToHotel(request, HotelId);
        return new ResponseEntity<>("true",HttpStatus.OK);

    }

    @GetMapping("/all/{HotelId}")
    @ResponseBody
    List<UslugeHotela> findAllHotelServices(@PathVariable Long HotelId) {
        return service.findAllHotelServices(HotelId);
    }


}
