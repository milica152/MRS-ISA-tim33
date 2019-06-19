package com.tim33.isa.service;

import com.tim33.isa.model.*;
import com.tim33.isa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = repository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);
        return user;
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRoles().getName()));

//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    public User save(User noviUser) {
        // Manipulacija profilom...

        noviUser.setPassword(bCryptPasswordEncoder.encode(noviUser.getPassword()));
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

        user1.setPassword(bCryptPasswordEncoder.encode(user1.getPassword()));

        repository.save(user1);

        return "true";
    }
}
