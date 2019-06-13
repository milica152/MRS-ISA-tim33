package com.tim33.isa.service;

import com.tim33.isa.model.*;
import com.tim33.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public User save(User noviUser) {
        // Manipulacija profilom...

        return repository.save(noviUser);
    }

    public User update(User noviUser) {
        // Manipulacija profilom...

        return repository.save(noviUser);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public String checkReg(User user1){
        //User user1 = (User) userr;

        if (user1.getIme().isEmpty()||user1.getPassword().isEmpty()||user1.getEmail().isEmpty()||user1.getPrezime().isEmpty()||
                user1.getUsername().isEmpty()){
            return "All fields are required!";
        }

        if (findByUsername(user1.getUsername()) != null){
            return "Username already taken!";
        }

        if (findByEmail(user1.getEmail()) != null){
            return "Email already taken!";
        }
        repository.save(user1);

        return "true";
    }
}
