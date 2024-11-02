package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;

public class ExcludeDepartureBeforeNowFilter implements FlightFilter {
    @Override
    public List<Flight> doFilter(List<Flight> flights) {

        return flights.stream().filter(f -> isDepartureBeforeNow(f.getSegments())).toList();
    }

    private boolean isDepartureBeforeNow(List<Segment> segments) {
        for (Segment segment : segments) {
           if(LocalDateTime.now().compareTo(segment.getDepartureDate()) <= 0) {
           return true;
           }
        }
        return false;
    }
}
