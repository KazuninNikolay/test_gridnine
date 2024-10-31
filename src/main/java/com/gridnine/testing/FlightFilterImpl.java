package com.gridnine.testing;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

class FlightFilterImpl implements FlightFilter {

    private List<Flight> flights;

    public FlightFilterImpl(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public List<Flight> excludeDepartureBeforeNow(List<Flight> flights) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (!isDepartureBeforeNow(flight)) {
                result.add(flight);
            }
        }
        return result;
    }

    @Override
    public List<Flight> excludeArrivalBeforeDeparture(List<Flight> flights) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            for (Segment segment : flight.getSegments()) {
                if (isArrivalAfterDeparture(segment)) {
                    result.add(flight);
                }
            }
        }
        return result;
    }

    @Override
    public List<Flight> excludeMoreTwoHoursOnGround(List<Flight> flights) {
        List<Flight> result = new ArrayList<>();
        for (Flight flight : flights) {
            if (isSumOnGroundMoreTwoHours(flight)) {
                result.add(flight);
            }
        }
        return result;
    }

    private boolean isSumOnGroundMoreTwoHours(Flight flight) {
        long limitTimeOnGroundInSecond = 2L * 60 * 60;
        long count = 0L;
        List<Segment> segments = flight.getSegments();
        for (int i = segments.size() - 1; i >= 1; i--) {
            count += ChronoUnit.SECONDS.between(flight.getSegments().get(i - 1).getDepartureDate(), flight.getSegments().get(i).getArrivalDate());
        }
        return count < limitTimeOnGroundInSecond;
    }

    private boolean isDepartureBeforeNow(Flight flight) {
        for (Segment segment : flight.getSegments()) {
            return segment.getDepartureDate().isBefore(LocalDateTime.now());
        }
        return true;
    }

    private static boolean isArrivalAfterDeparture(Segment segment) {
        return segment.getArrivalDate().isAfter(segment.getDepartureDate());

    }
}
