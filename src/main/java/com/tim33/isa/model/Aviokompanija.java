package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class Aviokompanija extends Service{

    @OneToMany(mappedBy = "airline") // inverse side: it has a mappedBy attribute, and can't decide how the association is mapped, since the other side already decided it.
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<Destinacija> destinations;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "airline")
    private Set<Karta> karteZaBrzu;

    //private HashMap<Integer, Double> cenovnik;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "airline")
    private Set<AirlineAdmin> admins;

//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aviokompanija")
//    private ArrayList<Let> letovi;

}
