package com.taxi.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.io.UncheckedIOException;

class TaxiRepositoryTest {

    @Test
    void loadsDriversFromJson() {
        TaxiData data = TaxiRepository.load();

        assertEquals("Онлайн-такси 24/7", data.getService());
        assertEquals("BYN", data.getCurrency());
        assertEquals(42, data.getDrivers().size());

        Driver first = data.getDrivers().get(0);
        assertEquals("Андрей", first.getName());
        assertEquals("Kia Rio", first.getCar());
        assertEquals("Эконом", first.getTariff());
        assertEquals(4.85, first.getRating(), 0.001);
        assertEquals(1240, first.getTrips());
        assertEquals(12896.00, first.getEarnings(), 0.001);
        assertTrue(first.isAvailable());
        assertEquals(10.40, first.getAverageFare(), 0.001);
    }

    @Test
    void driverWithoutTripsHasZeroFare() {
        Driver novice = TaxiRepository.load("zero-trips.json").getDrivers().get(0);
        assertEquals(0, novice.getTrips());
        assertEquals(0, novice.getAverageFare(), 0.001);   // без деления на ноль
        assertFalse(novice.isAvailable());
    }

    @Test
    void missingFileIsReported() {
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> TaxiRepository.load("no-such-file.json"));
        assertTrue(e.getMessage().contains("no-such-file.json"));
    }

    @Test
    void brokenJsonIsReported() {
        UncheckedIOException e = assertThrows(UncheckedIOException.class,
                () -> TaxiRepository.load("broken.json"));
        assertTrue(e.getMessage().contains("broken.json"));
    }
}
