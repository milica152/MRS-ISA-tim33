package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "avion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Avion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String aCode;
    private int numberOfRows;
    private int columnNumber;
    private int seatsPerColumn;
}
