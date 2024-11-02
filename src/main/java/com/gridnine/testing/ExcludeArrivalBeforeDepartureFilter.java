package com.gridnine.testing;

import java.util.List;
import java.util.stream.Collectors;

public class ExcludeArrivalBeforeDepartureFilter implements FlightFilter {
    @Override
    public List<Flight> doFilter(List<Flight> flights) {

        return flights.stream().filter(f -> isArrivalBeforeDepartment(f.getSegments())).collect(Collectors.toList());
    }

    private boolean isArrivalBeforeDepartment(List<Segment> segments) {
        for (Segment segment : segments) {
            if (segment.getArrivalDate().isBefore(segment.getDepartureDate())) {
                return false;
            }
        }
        return true;
    }

}
