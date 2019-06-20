package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "usluge_hotela")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UslugeHotela {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String naziv;
    private double cena;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH },fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    @JsonIgnore
    private Hotel hotel;

    @JsonIgnore
    @OneToMany(mappedBy = "hotelServices")
    private Set<HotelReservation> reservations;




}
