package com.tim33.isa.controller;

import com.tim33.isa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
