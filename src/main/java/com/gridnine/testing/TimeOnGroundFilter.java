package com.gridnine.testing;


import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

class TimeOnGroundFilter implements FlightFilter {
    private final long limitTimeOnGroundInSecond;

    public TimeOnGroundFilter(long limitTimeOnGroundInHours) {
        this.limitTimeOnGroundInSecond = limitTimeOnGroundInHours * 60 * 60;
    }

    @Override
    public List<Flight> doFilter(List<Flight> flights) {

        return flights.stream().filter(f -> isSumOnGroundMoreLimitHours(f.getSegments())).collect(Collectors.toList());

    }

    private boolean isSumOnGroundMoreLimitHours(List<Segment> segments) {
        long count = 0L;
        for (int i = 1; i < segments.size(); i++) {
            count += ChronoUnit.SECONDS.between(segments.get(i - 1).getArrivalDate(), segments.get(i).getDepartureDate());
        }
        return limitTimeOnGroundInSecond >= count;
    }
}
