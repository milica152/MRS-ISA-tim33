package com.tim33.isa.controller;

import com.tim33.isa.dto.filter.FilterFlight;
import com.tim33.isa.dto.filter.SearchFlight;
import com.tim33.isa.model.Let;
import com.tim33.isa.service.LetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Flight")
public class LetController {
    @Autowired
    LetService service;

    @PostMapping
    @ResponseBody
    Let save(@RequestBody Let novoVozilo) {
        return service.save(novoVozilo);
    }

    @PutMapping("/{id}")
    @ResponseBody
    Let update(@RequestBody Let noviLet, @PathVariable long id) {
        noviLet.setId(id);
        return service.save(noviLet);
    }

    @GetMapping("/all")
    @ResponseBody
    List<Let> findAll() {
        return service.findAll();
    }

    @GetMapping("/specific/{id}")
    @ResponseBody
    Let findById(@PathVariable long id) {
        return service.findById(id);
    }


    @RequestMapping(value = "deleteLet/{idDel}", method = RequestMethod.POST)
    @ResponseBody
    public void deleteFlight(@PathVariable Long idDel){service.deleteById(idDel);}


    @GetMapping("/fromAviocompany/{idAviocomp}")
    ResponseEntity<List<Let>> findAllFromAviocompany(@PathVariable long idAviocomp) {
        return new ResponseEntity<>(service.findAllFromAviocompany(idAviocomp), HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity<List<Let>> searchFlight(@Valid @RequestBody SearchFlight criteria) {
            return new ResponseEntity<>(service.searchFlight(criteria), HttpStatus.OK);
    }

    @RequestMapping(value="/filter", method = RequestMethod.POST)
    public ResponseEntity<?> filter(@RequestBody FilterFlight params) {
        if(params.getMaxPrice() == 0){
            params.setMaxPrice(99999999999999999.99);
        }
        try {
            if(params.getMinPrice()>params.getMaxPrice()){
                return new ResponseEntity<String>("Bad price range", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<List<Let> >(service.filter(params), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/dailyReport/{idAviocomp}")
    @ResponseBody
    ResponseEntity<?> findAllDaily(@PathVariable long idAviocomp, @RequestBody String date){
        try {
            return new ResponseEntity<int[]>(service.findAllDaily(idAviocomp, date), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/weeklyReport/{idAviocomp}")
    @ResponseBody
    ResponseEntity<?> findWeeklyReport(@PathVariable long idAviocomp){
        try {
            return new ResponseEntity<int[]>(service.findWeeklyReport(idAviocomp), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/monthlyReport/{idAviocomp}")
    @ResponseBody
    ResponseEntity<?> findMonthlyReport(@PathVariable long idAviocomp){
        try {
            return new ResponseEntity<int[]>(service.findMonthlyReport(idAviocomp), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/incomeReport/{idAviocomp}")
    @ResponseBody
    ResponseEntity<?> findIncomeReport(@PathVariable long idAviocomp, @RequestBody String dates){
        try {
            return new ResponseEntity<Double>(service.findIncomeReport(idAviocomp, dates), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
