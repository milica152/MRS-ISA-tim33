package com.tim33.isa.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterPretrageVozila {

    private LocalDate datumPreuzimanja;
    private LocalDate datumVracanja;
    private String mestoPreuzimanja;
    private String mestoVracanja;
    private int tipVozila;
    private int brojPutnika;
    private double cenaOd;
    private double cenaDo;

}
