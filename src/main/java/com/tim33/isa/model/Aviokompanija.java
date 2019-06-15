package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@Entity
@Table(name = "aviokompanija")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aviokompanija extends Service {

    private double ocena;

    //@OneToMany(mappedBy = "airline")
    //@Fetch(FetchMode.JOIN)
    //@JsonIgnore
    //private Set<Destinacija> destinations;

    //@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "airline")
    //private Set<Karta> karteZaBrzu;

    //@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "airline")
    //private Set<AirlineAdmin> admins;

}
