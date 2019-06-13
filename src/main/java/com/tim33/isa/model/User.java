package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "username", nullable = false)
    //@NotEmpty(message = "Username may not be empty")
    private String username;

    @Column(name = "password", nullable = false)
    //@NotEmpty(message = "Password may not be empty")
    private String password;

    @Column(name = "ime", nullable = false)
    //@NotEmpty(message = "First name may not be empty")
    private String ime;

    @Column(name = "prezime", nullable = false)
    //@NotEmpty(message = "Last name may not be empty")
    private String prezime;

    @Column(name = "email", nullable = false)
    //@NotEmpty(message = "Email may not be empty")
    //@Email(message="Email format is not correct.")
    private String email;

    @Column(name = "tip_korisnika", nullable = false)
    private TipUsera tip_korisnika;


}