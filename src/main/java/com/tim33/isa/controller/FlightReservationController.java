package com.tim33.isa.controller;

import com.tim33.isa.model.FlightReservation;
import com.tim33.isa.model.FlightReservationToAdd;
import com.tim33.isa.model.Let;
import com.tim33.isa.model.Sediste;
import com.tim33.isa.service.FlightReservationService;
import com.tim33.isa.service.LetService;
import com.tim33.isa.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/FlightReservationController")
public class FlightReservationController {
    @Autowired
    FlightReservationService service;
    @Autowired
    SeatService seatService;
    @Autowired
    LetService flightService;

    @PostMapping
    @ResponseBody
    FlightReservation save(@Valid  @RequestBody FlightReservationToAdd newFlightReservation) {
        FlightReservation fr = new FlightReservation();

        Date d = new Date(Long.parseLong(newFlightReservation.getDate()));
        fr.setTime(d);


        List<Let> flights = flightService.findAll();
        for(Let l : flights){
            if(l.getId() == Long.parseLong(newFlightReservation.getFlightId())){
                fr.setFlight(l);
                fr.setPrice(l.getCena());
                break;
            }

        }
        //seat
        List<Sediste> seats = seatService.findAll();
        String[] rowAndColumn = newFlightReservation.getSeatId().split("-");
        String row = rowAndColumn[0];
        String column = rowAndColumn[1];
        for(Sediste s : seats){
            if(s.getNumberOfRow() == Integer.parseInt(row) && s.getColumnNumber() == Integer.parseInt(column)  && s.getFlight().getId() == Long.parseLong(newFlightReservation.getFlightId())){
                fr.setSeat(s);
                Sediste seat = seatService.findById(s.getId());
                seat.setReserved(true);
                seatService.update(seat);
                break;
            }
        }


        //obradi podatke
        fr.setName(newFlightReservation.getName());
        fr.setSurname(newFlightReservation.getSurname());
        fr.setPassportNum(newFlightReservation.getPassport());


        return service.save(fr);
    }

    @GetMapping("/all")
    @ResponseBody
    List<FlightReservation> findAll() {
        return service.findAll();
    }

    @GetMapping("/specific/{id}")
    @ResponseBody
    FlightReservation findById(@PathVariable long id) {
        return service.findById(id);
    }


    @GetMapping("/fromFlight/{id}")
    ResponseEntity<List<FlightReservation>> findAllFromFlight(@PathVariable long idFlight) {
        return new ResponseEntity<>(service.findAllFromFlight(idFlight), HttpStatus.OK);
    }




}
