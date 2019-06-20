package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="HotelReservations")
public class HotelReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date beginDate;
    private Date endDate;
    private Double price;
    private Double discount=0.0;
    private Long hotelId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reservation_room", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"))
    private Set<Soba> room;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "reservation_services", joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    protected Set<UslugeHotela> hotelServices;


}
