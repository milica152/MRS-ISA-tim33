package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "filijale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Filijala {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String grad;
    private String adresa;
    private String brojZaposlenih;

    @ManyToOne(fetch = FetchType.EAGER)
    private RentACar rentACar;
}
