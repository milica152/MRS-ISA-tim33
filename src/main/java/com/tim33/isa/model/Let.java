package com.tim33.isa.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date vremePoletanja;
    private Date vremeSletanja;
    private int vremePutovanja;
    private int duzinaPutovanja;
    private ArrayList<Lokacija> presedanja;
    private double cena;




}
