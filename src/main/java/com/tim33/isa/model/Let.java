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
    private long id;
    private Date vremePoletanja;
    private Date vremeSletanja;
    private int vremePutovanja;
    private int duzinaPutovanja;
    private ArrayList<String> presedanja;
    private double cena;
    private KlasaLeta klasa;
    private TipPuta tipPuta;
    private double ocena;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Aviokompanija aviokompanija;



}
