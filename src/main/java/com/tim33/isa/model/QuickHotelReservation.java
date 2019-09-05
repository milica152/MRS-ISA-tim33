package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="HotelReservations")
public class QuickHotelReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double discount = 0.0;
    private Date beginDate;
    private Date endDate;
    private double price;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room")
    private Soba room;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "quickReservation_services", joinColumns = @JoinColumn(name = "quickReservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    protected Set<UslugeHotela> hotelServices;

}
