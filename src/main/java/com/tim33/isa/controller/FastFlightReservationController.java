package com.tim33.isa.controller;

import com.tim33.isa.model.*;
import com.tim33.isa.service.FastFlightReservationService;
import com.tim33.isa.service.LetService;
import com.tim33.isa.service.SeatService;
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
    @Autowired
    SeatService seatService;

    @RequestMapping(value = "/addFastRes", method = RequestMethod.POST)
    @ResponseBody
    FastFlightReservation save(@RequestBody FastFlightReservation newRes) {
        return service.save(newRes);
    }

    @PutMapping("/{id}")
    @ResponseBody
    FastFlightReservation update(@RequestBody FastFlightReservation newRes, @PathVariable long id) {
        return service.save(newRes);
    }


    //@RequestMapping(value = "", method = RequestMethod.POST)
    @PostMapping("/addFastReservation")
    @ResponseBody
    String addRes(@RequestBody QuickResInfo info) {
        List<FastFlightReservation> frs = service.findAll();
        List<Let> all = flightService.findAll();
        FastFlightReservation fr = new FastFlightReservation();
        List<Sediste> seats = seatService.findAll();

        try{
            for(FastFlightReservation ffr : frs){
                if(ffr.getFlight().getId() == Long.parseLong(info.getFlightId()) && ffr.getSeat().getColumnNumber() == Integer.parseInt(info.getColumn()) && ffr.getSeat().getNumberOfRow() == Integer.parseInt(info.getRow())){
                    return "There is already a quick reservation for this seat";
                }
            }
        }catch (Exception ex){
            return "Row and column are positive numbers.";
        }


        try{
            if(Integer.parseInt(info.getDiscount()) > 100 || Integer.parseInt(info.getDiscount()) <=0){
                return "Discount must be between 0 and 100.";
            }

        }catch (Exception ex){
            return "Discount must be between 0 and 100.";
        }

        for(Let let : all){

            if(let.getId() == Long.parseLong(info.getFlightId())){
                fr.setFlight(let);
                break;
            }
        }
        if(fr.getFlight() == null){
            return "Cannot find appropriate flight.";
        }

        try{
            for(Sediste s : seats){
                if(!s.isReserved() && s.getNumberOfRow() == Integer.parseInt(info.getRow()) && s.getColumnNumber() == Integer.parseInt(info.getColumn()) && s.getFlight().getId() == Long.parseLong(info.getFlightId())){
                    fr.setSeat(s);
                    break;
                }
            }
        }catch(Exception ex){
            return "Invalid data.";
        }

        if(fr.getSeat() == null){
            return "Cannot find that seat.";
        }

        fr.setArrCity(fr.getFlight().getOdredisniAerodrom().getGrad());
        fr.setArrCountry(fr.getFlight().getOdredisniAerodrom().getDrzava());
        fr.setArrTime(fr.getFlight().getVremeDolaska());
        fr.setDepCity(fr.getFlight().getPolazniAerodrom().getGrad());
        fr.setDepCountry(fr.getFlight().getPolazniAerodrom().getDrzava());
        fr.setDepTime(fr.getFlight().getVremePolaska());
        fr.setDiscount(Double.parseDouble(info.getDiscount())/100.0);
        fr.setKlasa(fr.getFlight().getKlasa());
        fr.setPrice(fr.getFlight().getCena());

        service.save(fr);
        return "Reservation successfully added.";
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
