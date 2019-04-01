package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sediste")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sediste {
    @Id
    private int broj;
    private boolean rezervisano;

}
