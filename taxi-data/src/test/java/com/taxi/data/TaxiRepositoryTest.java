package com.taxi.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaxiRepositoryTest {

    @Test
    void loadsDriversFromJson() {
        TaxiData data = TaxiRepository.load();

        assertEquals("BYN", data.getCurrency());
        assertEquals(42, data.getDrivers().size());

        Driver first = data.getDrivers().get(0);
        assertEquals("Андрей", first.getName());
        assertEquals("Kia Rio", first.getCar());
        assertTrue(first.isAvailable());
        assertEquals(10.40, first.getAverageFare(), 0.001);
    }
}
