package com.taxi.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.taxi.data.Driver;
import org.junit.jupiter.api.Test;

import java.util.List;

class TaxiServiceTest {

    private final TaxiService service = TaxiService.fromJson();

    @Test
    void filtersByTariff() {
        List<Driver> comfort = service.search("Комфорт", null, false);
        assertEquals(4, comfort.size());
        assertEquals("Егор", comfort.get(0).getName());
    }

    @Test
    void searchesByNameOrCar() {
        assertEquals(2, service.search(null, "kia", false).size());
        assertEquals(1, service.search(null, "Максим", false).size());
    }

    @Test
    void showsOnlyAvailable() {
        assertEquals(7, service.search(null, null, true).size());
    }

    @Test
    void countsStatistics() {
        Statistics all = service.statistics(service.getAllDrivers());
        assertEquals(10, all.getCount());
        assertEquals(9930, all.getTotalTrips());
        assertEquals(14.00, all.getAverageFare(), 0.01);
        assertEquals("Егор", all.getBest().getName());
    }

    @Test
    void groupsByRating() {
        assertEquals(List.of(7L, 1L, 2L, 0L), List.copyOf(service.ratingGroups().values()));
    }
}
