package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "vozila")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vozilo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naziv;
    private String marka;
    private double cena;
    private int tipVozila;
    private int brojMesta = 5;

    @ManyToOne(fetch = FetchType.EAGER)
    private RentACar rentACar;
}
