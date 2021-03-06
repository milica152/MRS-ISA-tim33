package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sediste")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sediste {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int numberOfRow;
    private int columnNumber;
    private boolean isReserved;

    @ManyToOne(fetch = FetchType.EAGER)
    private Let flight;

}
