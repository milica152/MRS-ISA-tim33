package com.tim33.isa.service;

import com.tim33.isa.model.User;
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


}
