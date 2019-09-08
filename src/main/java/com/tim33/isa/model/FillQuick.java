package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FillQuick {

    private Long id;
    private Double price;
    private Double priceBefore;
    private Double discount;
    private Date dateFrom;
    private Date dateTo;
    private String[] services;


}
