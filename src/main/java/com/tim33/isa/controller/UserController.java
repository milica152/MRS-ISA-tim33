package com.tim33.isa.controller;

import com.tim33.isa.model.User;
import com.tim33.isa.model.UserInfo;
import com.tim33.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/whoami")
    public UserDetails getUser() {
        Object a = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (a.equals("anonymousUser")) return null;
        return (UserDetails) a;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/editInfo")
    @ResponseBody
    public String editInfo(@Valid @RequestBody UserInfo ui) {

        try{
            UserDetails ud = getUser();
            User user = userService.findByUsername(ud.getUsername());
            user.setEmail(ui.getEmail());
            user.setIme(ui.getName());
            user.setPassword(ui.getPassword());
            user.setPrezime(ui.getSurname());
            userService.update(user);
        }catch (Exception ex){
            return "Problem with autentification.";
        }
        return "ok";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/findByUsername")
    @ResponseBody
    public User findByUsername (@RequestBody String username){
        username = username.substring(0,username.length()-1);
        User u =  userService.findByUsername(username);
        return u;
    }

}
