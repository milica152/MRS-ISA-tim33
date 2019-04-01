package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rc_servis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentACar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naziv;
    private String adresa;
    private String promotivniOpis;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rentACar")
    @JsonIgnore
    private Set<Vozilo> vozila;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rentACar")
    @JsonIgnore
    private Set<Filijala> filijale;
}
