package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Aviokompanija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naziv;
    private String adresa;
    private String opis;
    private ArrayList<Destinacija> destinacije;
    private ArrayList<Karta> karteZaBrzu;
    //private HashMap<Integer, Double> cenovnik;

//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aviokompanija")
//    private ArrayList<Let> letovi;

}
