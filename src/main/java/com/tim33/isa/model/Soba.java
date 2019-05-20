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
@Table(name = "sobe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Soba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int ocena;
    private Date pocetak_rezervacije;
    private Date kraj_rezervacije;
    private TipSobe tip_sobe;
    private double cena_nocenja;
    private ArrayList<UslugeHotela> usluga;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airline")
    private Hotel hotel;

}
