package com.tim33.isa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightReservationToAdd {
    private String name;
    private String surname;
    private String passport;
    private String flightId;
    private String seatId;
    private String date;

}
