package com.tim33.isa.dto.filter;

import com.tim33.isa.model.Let;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterFlight {
    protected double minPrice;
    protected double maxPrice;
    protected String airline;
    protected int duration;
    protected List<Integer> flights;
}
