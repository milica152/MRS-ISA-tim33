package com.tim33.isa.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LetZaDodavanje {
    private String cena;
    private String vreme_poletanja;
    private String vreme_sletanja;
    private String datum_poletanja;
    private String datum_sletanja;
    private String duzina_polazak;
    private String duzina_povratak;
    private String tip;
    private String klasa;
    private String aviokompanija_id;
    private String odredisni_aerodrom_id;
    private String polazni_aerodrom_id;



}
