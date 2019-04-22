package com.tim33.isa.controller;

import com.tim33.isa.model.TipUsera;
import com.tim33.isa.model.User;
import com.tim33.isa.repository.UserRepository;
import com.tim33.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @RequestMapping(value = "registerUser", method = RequestMethod.POST)
    public ResponseEntity<?> register(@Valid @RequestBody User user) {


        User u = new User();
        u.setId(0L);
        u.setIme(user.getIme());
        u.setPassword(user.getPassword());
        u.setPrezime(user.getPrezime());
        u.setUsername(user.getUsername());
        u.setEmail(user.getEmail());
        u.setTip_korisnika(TipUsera.OBICAN);
        if(userRepository.findByUsername(u.getUsername())!=null){
            return new ResponseEntity<>("Username already taken!", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.findByEmail(u.getEmail())!=null){
            return new ResponseEntity<>("Email already taken!", HttpStatus.BAD_REQUEST);
        }
        userRepository.save(u);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);


    }
}
