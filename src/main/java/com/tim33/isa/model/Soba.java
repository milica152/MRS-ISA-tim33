package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sobe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Soba {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Integer roomNo;
    private Integer roomFloor;
    private Integer ocena;
    private TipSobe tip_sobe;
    private Double cena_nocenja;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel")
    private Hotel hotel;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private Set<HotelReservation> reservations;


}
