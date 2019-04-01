package com.tim33.isa.model;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "aviokompanija")

@NoArgsConstructor
@AllArgsConstructor
public class Aviokompanija {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private long id;
    private String naziv;
    private String adresa;
    private String opis;
    private double ocena;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "aviokompanija")
    private ArrayList<Destinacija> destinacije;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aviokompanija")
    private ArrayList<Karta> karteZaBrzu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aviokompanija")
    private ArrayList<Let> letovi;


}
