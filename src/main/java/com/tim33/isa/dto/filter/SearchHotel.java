package com.tim33.isa.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchHotel {

    protected String nameOrDestination;
    protected String dateFrom;
    protected String dateTo;
}
