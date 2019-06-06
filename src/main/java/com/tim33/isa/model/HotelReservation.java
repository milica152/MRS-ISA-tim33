package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room")
    private Soba room;

    @OneToMany(mappedBy = "reservation") // inverse side: it has a mappedBy attribute, and can't decide how the association is mapped, since the other side already decided it.
    @Fetch(FetchMode.JOIN)
    @JsonIgnore
    private Set<UslugeHotela> usluga;


}
