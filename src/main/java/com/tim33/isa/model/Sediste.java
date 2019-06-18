package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sediste")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sediste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int brojReda;
    private int brojKolone;
    private boolean rezervisano;

    @ManyToOne(fetch = FetchType.EAGER)
    private Avion avion;
}
