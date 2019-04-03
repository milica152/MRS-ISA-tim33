package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "lokacijaPresedanja")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LokacijaPresedanja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String sifraAerodroma;
    private String nazivAerodroma;
    private String grad;
    private String drzava;
}
