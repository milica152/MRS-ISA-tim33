package com.tim33.isa.service;

import com.tim33.isa.model.Let;
import com.tim33.isa.repository.LetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LetService {
    @Autowired
    LetRepository repository;

    public Let save(Let noviLet){
        // Manipulacija letom...
        return repository.save(noviLet);
    }

    public Let update(Let noviLet) {
        // Manipulacija letom...
        return repository.save(noviLet);
    }

    public List<Let> findAll() {
        return repository.findAll();
    }

    public Let findById(long id) {
        return repository.findById(id).orElse(null);
    }

}
