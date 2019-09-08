package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "fast_flight_reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FastFlightReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double price;
    private String name;
    private String surname;
    private String passportNum;
    private double discount;
    private String depCity;
    private String depCountry;
    private String arrCity;
    private String arrCountry;
    private KlasaLeta klasa;
    private Date depTime;
    private Date arrTime;

    @OneToOne(cascade = CascadeType.ALL)
    private Sediste seat;

    @ManyToOne(fetch = FetchType.EAGER)
    private Let flight;
}
