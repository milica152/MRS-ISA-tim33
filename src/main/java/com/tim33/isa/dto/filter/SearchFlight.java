package com.tim33.isa.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchFlight {
    protected String dateFrom;    //samo datum bez vremena se cuva
    protected String dateTo;
    protected String departureAirport;
    protected String arrivalAirport;
    protected String NoPassengers;
    protected String type;
    protected String klasa;
}
