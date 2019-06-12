package com.tim33.isa.dto.filter;

import com.tim33.isa.model.Hotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterRooms {

    private List<String> roomTypeList;
    private double priceFrom;
    private double priceTo;
}
