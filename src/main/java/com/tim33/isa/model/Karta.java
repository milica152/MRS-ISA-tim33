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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    private Let let;
    private String imePutnika;
    private String prezimePutnika;
    private int brojSedista;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Aviokompanija aviokompanija;

}
