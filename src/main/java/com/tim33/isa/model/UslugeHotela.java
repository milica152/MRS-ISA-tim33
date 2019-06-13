package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "usluge_hotela")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UslugeHotela {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naziv;
    private double cena;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH },fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    @JsonIgnore
    private Hotel hotel;

    @JsonIgnore
    @ManyToOne
    @JoinTable(name="reservation_services",
            joinColumns={@JoinColumn(name="service_id")},
            inverseJoinColumns={@JoinColumn(name="reservation_id")})
    private HotelReservation reservation;




}
