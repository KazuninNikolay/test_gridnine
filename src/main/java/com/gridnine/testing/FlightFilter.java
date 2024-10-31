package com.gridnine.testing;

import java.util.List;

public interface FlightFilter {

    List<Flight> excludeDepartureBeforeNow(List<Flight> flights);
    List<Flight> excludeArrivalBeforeDeparture(List<Flight> flights);
    List<Flight> excludeMoreTwoHoursOnGround(List<Flight> flights);


}
