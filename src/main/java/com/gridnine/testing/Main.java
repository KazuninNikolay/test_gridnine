package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Flight> flights = FlightBuilder.createFlights();

        FlightFilterExecutor flightFilterExecutor = new FlightFilterExecutor();

        flightFilterExecutor.addFilter(new ExcludeArrivalBeforeDepartureFilter());
        System.out.println(flightFilterExecutor.execute(flights));

        flightFilterExecutor.clearListFilters();
        System.out.println("-".repeat(100) + "\n");

        flightFilterExecutor.addFilter(new ExcludeDepartureBeforeNowFilter());
        System.out.println(flightFilterExecutor.execute(flights));

        flightFilterExecutor.clearListFilters();
        System.out.println("-".repeat(100) + "\n");

        flightFilterExecutor.addFilter(new TimeOnGroundFilter(2L));
        System.out.println(flightFilterExecutor.execute(flights));
    }
}
