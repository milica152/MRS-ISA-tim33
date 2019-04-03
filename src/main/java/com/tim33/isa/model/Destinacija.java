package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "destinacija")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Destinacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String grad;
    private String drzava;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JsonIgnore
//    private Aviokompanija aviokompanija;

}
