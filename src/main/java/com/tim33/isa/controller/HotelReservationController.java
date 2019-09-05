package com.tim33.isa.controller;
import com.tim33.isa.model.HotelReservation;
import com.tim33.isa.model.Soba;
import com.tim33.isa.service.HotelReservationService;
import com.tim33.isa.service.HotelService;
import com.tim33.isa.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/HotelReservation")
public class HotelReservationController {

    @Autowired
    HotelReservationService service;
    @Autowired
    RoomService roomService;
    @Autowired
    HotelService hotelService;

    @RequestMapping(value = "/add/{hotelId}", method = RequestMethod.POST)
    public ResponseEntity<?> addHotelReservation(@PathVariable Long hotelId) {
        HotelReservation reservation = new HotelReservation();
        reservation.setHotelId(hotelId);
        service.save(reservation);
        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }
    @RequestMapping(value = "/addRoom/{ReservationId}", method = RequestMethod.POST)
    public ResponseEntity<?> addRoomToReservation(@RequestBody Soba room, @PathVariable Long ReservationId) {
        HotelReservation reservation = service.addRoomToReservation(room, service.findById(ReservationId));
        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }

    @RequestMapping(value = "/deleteRoom/{ReservationId}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteRoomFromReservation(@RequestBody Soba room, @PathVariable Long ReservationId) {
        HotelReservation reservation = service.deleteRoomFromReservation(room, ReservationId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }

    @RequestMapping(value = "/deleteRoomCart/{RoomId}/{ReservationId}", method = RequestMethod.POST)
    public ResponseEntity<?> deleteRoomFromReservation(@PathVariable Long ReservationId, @PathVariable Long RoomId) {
        HotelReservation reservation = service.deleteRoomFromReservation(roomService.findById(RoomId), ReservationId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @RequestMapping(value = "/addServices/{reservationId}", method = RequestMethod.POST)
    public ResponseEntity<?> addServicesToReservation(String[] services, @PathVariable Long reservationId){
        //String[] services = inputArray.split(",");
        HotelReservation reservation = service.addServicesToReservation(services, reservationId);
        if(reservation==null){
            return new ResponseEntity<>("You must choose room first!", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        }

    }
}
