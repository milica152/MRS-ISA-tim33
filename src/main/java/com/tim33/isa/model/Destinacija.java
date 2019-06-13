package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.Collection;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

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

    @JsonIgnore
    @ManyToOne
    @JoinTable(name="airline_destination",
            joinColumns={@JoinColumn(name="destination_id")},
            inverseJoinColumns={@JoinColumn(name="airline_id")})
    private Aviokompanija airline;


}
