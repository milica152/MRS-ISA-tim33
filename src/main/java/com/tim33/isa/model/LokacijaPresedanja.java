package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lokacijaPresedanja")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LokacijaPresedanja {
    @Id
    private String sifraAerodroma;
    private String nazivAerodroma;
    private String grad;
    private String drzava;
}
