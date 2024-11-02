package com.gridnine.testing;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class FlightFilterExecutorTest {

    FlightFilterExecutor filterExecutor = new FlightFilterExecutor();

    Flight arrivalBeforeDeparture = new Flight(Arrays.asList(
            new Segment(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2)),
            new Segment(LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(2)),
            new Segment(LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(5))
    ));
    Flight departureBeforeNow = new Flight(Arrays.asList(
            new Segment(LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(1)),
            new Segment(LocalDateTime.now(), LocalDateTime.now().plusHours(1))
    ));

    Flight threeHoursOnGround = new Flight(Arrays.asList(
            new Segment(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2)),
            new Segment(LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4)),
            new Segment(LocalDateTime.now().plusHours(6), LocalDateTime.now().plusHours(6))
    ));

    Flight normalFlight = new Flight(List.of(new Segment(LocalDateTime.now().plusMinutes(1), LocalDateTime.now().plusHours(2))));
    List<Flight> flights = Arrays.asList(arrivalBeforeDeparture, departureBeforeNow, threeHoursOnGround, normalFlight);

    @AfterEach
    void clearFilters() {
        filterExecutor.clearListFilters();
    }

    @Test
    void execute() {
        List<Flight> expected = List.of(normalFlight);
        filterExecutor.addFilter(new ExcludeArrivalBeforeDepartureFilter());
        filterExecutor.addFilter(new ExcludeDepartureBeforeNowFilter());
        filterExecutor.addFilter(new TimeOnGroundFilter(2L));
        List<Flight> actual = filterExecutor.execute(flights);

        Assertions.assertEquals(expected , actual);
    }

    @Test
    void executeArrivalBeforeDepartureFilter() {
        List<Flight> expected = Arrays.asList(departureBeforeNow, threeHoursOnGround, normalFlight);
        filterExecutor.addFilter(new  ExcludeArrivalBeforeDepartureFilter());
        List<Flight> actual = filterExecutor.execute(flights);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void executeDepartureBeforeNowFilter() {
        List<Flight> expected = Arrays.asList(arrivalBeforeDeparture, threeHoursOnGround, normalFlight);
        filterExecutor.addFilter(new ExcludeDepartureBeforeNowFilter());
        List<Flight> actual = filterExecutor.execute(flights);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void executeTimeOnGroundFilter() {
        long twoHours = 2L;
        List<Flight> expected = Arrays.asList(arrivalBeforeDeparture, departureBeforeNow, normalFlight);
        filterExecutor.addFilter(new TimeOnGroundFilter(twoHours));
        List<Flight> actual = filterExecutor.execute(flights);

        Assertions.assertEquals(expected, actual);
    }

}