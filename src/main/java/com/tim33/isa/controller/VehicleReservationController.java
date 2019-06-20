package com.tim33.isa.controller;

import com.tim33.isa.model.VehicleReservation;
import com.tim33.isa.model.Vozilo;
import com.tim33.isa.service.RentACarService;
import com.tim33.isa.service.VehicleReservationService;
import com.tim33.isa.service.VozilaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/VehicleReservation")
public class VehicleReservationController {

    @Autowired
    VehicleReservationService service;

    @Autowired
    VozilaService vozilaService;

    @Autowired
    RentACarService rentACarService;

    @RequestMapping(value = "/add/{rcID}", method = RequestMethod.POST)
    public ResponseEntity<?> addVehicleReservation(@PathVariable Long rcID) {
        VehicleReservation reservation = new VehicleReservation();
        reservation.setRentACar(rentACarService.findById(rcID));
        service.save(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }
    @RequestMapping(value = "/addVehicle/{reservationId}/{voziloId}", method = RequestMethod.POST)
    public ResponseEntity<?> addVehicleToReservation(@PathVariable Long reservationId, @PathVariable Long voziloId) {
        VehicleReservation reservation = service.addVehicleToReservation(vozilaService.findById(voziloId), service.findById(reservationId));
        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }

    @RequestMapping(value = "/deleteVehicle/{reservationId}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteVehicleFromReservation(@RequestBody Vozilo vozilo, @PathVariable Long reservationId) {
        VehicleReservation reservation = service.deleteVehicleFromReservation(vozilo, reservationId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }

    @RequestMapping(value = "/deleteVehicleCart/{voziloId}/{ReservationId}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteVehicleFromReservation(@PathVariable Long ReservationId, @PathVariable Long voziloId) {
        VehicleReservation reservation = service.deleteVehicleFromReservation(vozilaService.findById(voziloId), ReservationId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }
}
