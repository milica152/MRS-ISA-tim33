package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "flight_reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double price;
    private Date time;

    @OneToOne(cascade = CascadeType.ALL)
    private Sediste seat;

    @ManyToOne(fetch = FetchType.EAGER)
    private Let flight;

}