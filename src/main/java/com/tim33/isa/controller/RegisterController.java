package com.tim33.isa.controller;

import com.tim33.isa.model.*;
import com.tim33.isa.repository.ServiceRepository;
import com.tim33.isa.repository.UserRoleRepository;
import com.tim33.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @RequestMapping(value = "registerUser", method = RequestMethod.POST)
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        user.setTip_korisnika(TipUsera.OBICAN);
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRole("OBICAN"))));
        String mess= userService.checkReg(user);
        if (!mess.equals("true")) {
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
    }
    @RequestMapping(value = "registerAdmin/{serviceName}", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody User user,@PathVariable("serviceName") String serviceName) {
        Service service = serviceRepository.findByNaziv(serviceName);
        String mess = null;
        if(service==null){
            mess = "Service doesn't exist";
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        }
        User admin = null;

        if(service instanceof Aviokompanija){
            admin = new AirlineAdmin();
            ((Aviokompanija) service).getAdmins().add((AirlineAdmin) admin);
            ((AirlineAdmin) admin).setAirline((Aviokompanija)service);
            admin.setTip_korisnika(TipUsera.ADMIN_AK);
            admin.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ADMIN_AK"))));
        } else if(service instanceof Hotel){
            admin = new HotelAdmin();
            ((Hotel) service).getAdmins().add((HotelAdmin) admin);
            ((HotelAdmin) admin).setHotel((Hotel)service);
            admin.setTip_korisnika(TipUsera.ADMIN_HOTELA);
            admin.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ADMIN_HOTELA"))));
        } else if(service instanceof RentACar){
            admin = new RCSAdmin();
            ((RentACar) service).getAdmins().add((RCSAdmin) admin);
            ((RCSAdmin) admin).setRentACar((RentACar)service);
            admin.setTip_korisnika(TipUsera.ADMIN_RCS);
            admin.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ADMIN_RCS"))));
        }
        admin.setId(0);
        admin.setPassword(user.getPassword());
        admin.setUsername(user.getUsername());
        admin.setIme(user.getIme());
        admin.setPrezime(user.getPrezime());
        admin.setEmail(user.getEmail());

        mess = userService.checkReg(admin);
        if (!mess.equals("true")) {
            return new ResponseEntity<>(mess, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/acc_confirm", method = RequestMethod.GET)
    public RedirectView confirmRegistration(@RequestParam String token) {
        return userService.confirmRegistration(token);
    }

}
