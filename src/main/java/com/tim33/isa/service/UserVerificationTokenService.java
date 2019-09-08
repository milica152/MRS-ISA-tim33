package com.tim33.isa.service;

import com.tim33.isa.model.UserVerificationToken;
import com.tim33.isa.repository.UserVerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserVerificationTokenService {

    @Autowired
    private UserVerificationTokenRepository repository;

    public UserVerificationToken save(UserVerificationToken token) {
        return repository.save(token);
    }

}
