package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "karta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Karta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
//    private Let let;
    private String imePutnika;
    private String prezimePutnika;
    private int brojSedista;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name="airline_id")
    private Aviokompanija airline;

}
