package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Flight> flights = FlightBuilder.createFlights();

        FlightFilter filter = new FlightFilterImpl(flights);

        System.out.println(filter.excludeArrivalBeforeDeparture(flights));
        System.out.println("-".repeat(100));
        System.out.println(filter.excludeMoreTwoHoursOnGround(flights));
        System.out.println("-".repeat(100));
        System.out.println(filter.excludeDepartureBeforeNow(flights));
    }
}
