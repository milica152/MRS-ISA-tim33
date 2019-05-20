package com.tim33.isa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rc_servis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentACar extends Service{

    @OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "rentACar")
    @JsonIgnore
    private Set<RCSAdmin> admins;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rentACar")
    @JsonIgnore
    private Set<Vozilo> vozila;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rentACar")
    @JsonIgnore
    private Set<Filijala> filijale;
}
