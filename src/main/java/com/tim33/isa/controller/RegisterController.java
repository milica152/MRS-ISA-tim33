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

    @RequestMapping(value = "registerUser", method = RequestMethod.POST)
    public ResponseEntity<?> register(@Valid @RequestBody User user) {

        String mess= userService.checkReg(user);
        if (!mess.equals("true")) {
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
    }
}
