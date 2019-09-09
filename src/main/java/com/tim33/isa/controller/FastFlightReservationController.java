package com.tim33.isa.controller;

import com.tim33.isa.model.FastFlightReservation;
import com.tim33.isa.model.Let;
import com.tim33.isa.model.ReservationInfo;
import com.tim33.isa.model.Sediste;
import com.tim33.isa.service.FastFlightReservationService;
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
@RequestMapping("/FastFlightReservation")
public class FastFlightReservationController {
    @Autowired
    FastFlightReservationService service;
    @Autowired
    LetService flightService;

    @RequestMapping(value = "addFastRes", method = RequestMethod.POST)
    @ResponseBody
    FastFlightReservation save(@RequestBody FastFlightReservation newRes) {
        return service.save(newRes);
    }

    @PutMapping("/{id}")
    @ResponseBody
    FastFlightReservation update(@RequestBody FastFlightReservation newRes, @PathVariable long id) {
        newRes.setId(id);    //??
        return service.save(newRes);
    }


    @GetMapping("/specific/{id}")
    @ResponseBody
    FastFlightReservation findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/seatsFromFlight/{id}")
    @ResponseBody
    List<Sediste> findAllByFlightId(@PathVariable long id) {
        List<Sediste> lista = new ArrayList<>();
        for(FastFlightReservation ffr :  service.findAllByFlightId(id)){
            lista.add(ffr.getSeat());
        }

        return lista;
    }

    @GetMapping("/fromAirline/{id}")
    @ResponseBody
    List<FastFlightReservation> findAllByAviocompanyId(@PathVariable long id) {


        List<FastFlightReservation> result = new ArrayList<>();
        List<FastFlightReservation> all = service.findAll();

        if(!all.isEmpty()){
            for(FastFlightReservation fr : all){
                if(fr.getFlight().getAviokompanija().getId() == id){
                    result.add(fr);
                }
            }
        }


        return result;
    }



    @RequestMapping(value = "/deleteFastRes/{idDel}", method = RequestMethod.POST)   //ako ajax posalje post metodom na ovu adr.
    @ResponseBody
    public void deleteFastRes(@PathVariable Long idDel){service.deleteById(idDel);}



    @GetMapping("/all")
    @ResponseBody
    List<FastFlightReservation> findAll() {
        return service.findAll();
    }


    @RequestMapping(value = "/reserve/{id}", method = RequestMethod.POST)   //ako ajax posalje post metodom na ovu adr.
    @ResponseBody
    public ResponseEntity<?> reserveFastRes(@Valid @PathVariable Long id, @RequestBody ReservationInfo info){
        //sediste se promeni na rezervisano
        FastFlightReservation ffr = service.findById(id);
        ffr.setName(info.getName());
        ffr.setSurname(info.getSurname());
        ffr.setPassportNum(info.getPassNum());
        String mess= service.reserve(ffr);   //napravi rez na servisu
        if (!mess.equals("true")) {
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }

    }





}
