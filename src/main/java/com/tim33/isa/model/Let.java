package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "let")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Let {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String sifra;
    private Date vremePolaska;
    private Date vremeDolaska;
    private int duzina;
    // Ovde ne moze da stoji array lista
    //private ArrayList<String> presedanja;
    private double cena;
    private double ocena;
    private KlasaLeta klasa;


    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Aviokompanija aviokompanija;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private LokacijaPresedanja polazniAerodrom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private LokacijaPresedanja odredisniAerodrom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Avion plane;
}
