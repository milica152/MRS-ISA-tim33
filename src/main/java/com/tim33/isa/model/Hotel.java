package com.tim33.isa.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
@Table(name = "hotel_servis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naziv;
    private String adresa;
    private String opis;
    private String cenovnik;
    private ArrayList<Soba> konfiguracija_soba;
    private int ocena;


}
