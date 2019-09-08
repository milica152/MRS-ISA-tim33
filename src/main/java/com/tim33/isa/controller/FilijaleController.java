package com.tim33.isa.controller;

import com.tim33.isa.model.Filijala;
import com.tim33.isa.service.FilijaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Filijale")
public class FilijaleController {
    
    @Autowired
    FilijaleService service;

    @PostMapping
    Filijala save(@RequestBody Filijala novaFilijala) {
        return service.save(novaFilijala);
    }

    @PutMapping("/{id}")
    ResponseEntity<?> update(@RequestBody Filijala novaFilijala, @PathVariable long id) {
        if (service.findById(id) != null) {
            novaFilijala.setId(id);
            return new ResponseEntity<>(service.save(novaFilijala), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no Branch with that ID", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    List<Filijala> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findById(@PathVariable long id) {
        Filijala filijala = service.findById(id);
        if (filijala != null) {
            return new ResponseEntity<>(filijala, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There is no Branch with that ID", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/fromRC/{idRentACara}")
    ResponseEntity<List<Filijala>> findAllFromRC(@PathVariable long idRentACara) {
        return new ResponseEntity<>(service.findAllFromRC(idRentACara), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable long id) {
        Object a = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (a.equals("anonymousUser")) return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        else {
            UserDetails user = (UserDetails) a;
            if (user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_RCS")) ||
                user.getAuthorities().contains(new SimpleGrantedAuthority("SISTEM_ADMIN"))) {
                service.delete(id);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }
        }
    }
}
