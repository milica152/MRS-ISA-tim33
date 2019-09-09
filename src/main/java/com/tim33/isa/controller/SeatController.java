package com.tim33.isa.controller;

import com.tim33.isa.model.Let;
import com.tim33.isa.model.Sediste;
import com.tim33.isa.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @PutMapping("/update")
    @ResponseBody
    Sediste update(@RequestBody Sediste newSeat) {
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


    @PostMapping("/rowAndColumn/{id}/{idFlight}")
    @ResponseBody
    Sediste findByRowAndColumn(@PathVariable String id, @PathVariable long idFlight) {
        int row = Integer.parseInt(id.split("-")[0]);
        int column = Integer.parseInt(id.split("-")[1]);

        return service.findByRowAndColumnAndFlight(row, column, idFlight);
    }


    @RequestMapping(value = "deleteSeat/{idDel}", method = RequestMethod.POST)
    @ResponseBody
    public void deleteSeat(@PathVariable Long idDel){service.deleteById(idDel);}


    @GetMapping("/fromPlane/{idFlight}")
    ResponseEntity<Sediste[][]> findAllFromPlane(@PathVariable long idFlight) {
        return new ResponseEntity<Sediste[][]>(service.findAllFromPlane(idFlight), HttpStatus.OK);
    }

    @PostMapping("/addRow/{idFlight}")
    @ResponseBody
    public List<Sediste> addRow(@PathVariable long idFlight){
        int max = 0;
        int numColumns = 0;
        Let flight = null;
        List<Sediste> result = new ArrayList<>();
        int colCounter = 0;
        int seatsPerColumn = 0;
        int emptySeatsNum = 0;

        try{
            List<Sediste> seats = service.findAll();
            for(Sediste seat : seats){
                if(seat.getFlight().getId() == idFlight && seat.getNumberOfRow()>max){
                    max = seat.getNumberOfRow();
                    flight = seat.getFlight();
                    emptySeatsNum = seat.getFlight().getPlane().getColumnNumber()-1;
                    seatsPerColumn = seat.getFlight().getPlane().getSeatsPerColumn();
                    numColumns = seat.getFlight().getPlane().getColumnNumber()* seat.getFlight().getPlane().getSeatsPerColumn();
                }
            }
            for(int i = 1 ; i <= numColumns + emptySeatsNum; i ++){
                if(i % (seatsPerColumn+1) == 0){
                    result.add(null);
                }else{
                    colCounter++;
                    Sediste s = new Sediste();
                    s.setFlight(flight);
                    s.setColumnNumber(colCounter);
                    s.setNumberOfRow(max+1);
                    s.setReserved(false);
                    result.add(s);
                    service.save(s);
                }

            }


        }catch (Exception ex){
            return new ArrayList<Sediste>();
        }
        return result;
    }

}
