package com.gridnine.testing;

import java.util.ArrayList;
import java.util.List;

public class FlightFilterExecutor {

    private final List<FlightFilter> filters = new ArrayList<>();

    public void addFilter(FlightFilter filter) {
        filters.add(filter);
    }

    public void deleteFilter(FlightFilter filter) {
        filters.remove(filter);
    }

    public List<FlightFilter> getFilters() {
        return filters;
    }

    public void clearListFilters() {
        filters.clear();
    }

    public List<Flight> execute(List<Flight> flights) {
        List<Flight> filteredFlights = flights;
        for (FlightFilter filter : filters) {
            filteredFlights = filter.doFilter(filteredFlights);
        }
        return filteredFlights;
    }

}
