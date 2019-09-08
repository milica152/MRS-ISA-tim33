package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_flight_reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFlightReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private FlightReservation fr;

}
