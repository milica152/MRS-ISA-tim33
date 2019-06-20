package com.tim33.isa.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="vehicle_reservation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private RentACar rentACar;

    private double price;
    private LocalDate datumPreuzimanja;
    private LocalDate datumVracanja;
    private String mestoPreuzimanja;
    private String mestoVracanja;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reservation_vehicles", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "vehicle_id", referencedColumnName = "id"))
    private Set<Vozilo> vozila;
}
