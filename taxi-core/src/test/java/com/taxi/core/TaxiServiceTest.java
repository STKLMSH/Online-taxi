package com.taxi.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.taxi.data.Driver;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class TaxiServiceTest {

    private final TaxiService service = TaxiService.fromJson();

    @Test
    void readsServiceInfo() {
        assertEquals("Онлайн-такси 24/7", service.getServiceName());
        assertEquals("BYN", service.getCurrency());
        assertEquals(List.of("Эконом", "Комфорт", "Бизнес"), service.getTariffs());
    }

    @Test
    void filtersByTariff() {
        List<Driver> comfort = service.search("Комфорт", null, false);
        assertEquals(16, comfort.size());
        assertEquals("Егор", comfort.get(0).getName());
    }

    @Test
    void searchesByNameOrCar() {
        assertEquals(4, service.search(null, "kia", false).size());
        assertEquals(1, service.search(null, "Максим", false).size());
    }

    @Test
    void ignoresBlankConditions() {
        assertEquals(42, service.search("  ", "   ", false).size());
    }

    @Test
    void showsOnlyAvailable() {
        assertEquals(32, service.search(null, null, true).size());
    }

    @Test
    void countsStatistics() {
        Statistics all = service.statistics(service.getAllDrivers());
        assertEquals(42, all.getCount());
        assertEquals(32, all.getAvailable());
        assertEquals(48520, all.getTotalTrips());
        assertEquals(15.38, all.getAverageFare(), 0.01);
        assertEquals(2.85, all.getMinRating(), 0.001);
        assertEquals(4.97, all.getMaxRating(), 0.001);
        assertEquals(4.26, all.getAverageRating(), 0.01);
        assertEquals("Егор", all.getBest().getName());
    }

    @Test
    void emptyListGivesZeroStatistics() {
        Statistics none = service.statistics(List.of());
        assertEquals(0, none.getCount());
        assertEquals(0, none.getAverageFare(), 0.001);   // без деления на ноль
        assertNull(none.getBest());
    }

    @Test
    void groupsByRating() {
        assertEquals(List.of(16L, 12L, 13L, 1L), List.copyOf(service.ratingGroups().values()));
    }

    @Test
    void buildsTariffReport() {
        Map<String, Statistics> report = service.tariffReport();
        assertEquals(List.of("Эконом", "Комфорт", "Бизнес"), List.copyOf(report.keySet()));
        assertEquals(8, report.get("Бизнес").getCount());
        assertEquals(31.56, report.get("Бизнес").getAverageFare(), 0.01);
    }

    @Test
    void exportsCsv() {
        String csv = service.toCsv();
        assertTrue(csv.startsWith("Отчёт;Онлайн-такси 24/7"));
        assertTrue(csv.contains("Итого;42;32;4,26;48520;15,38"));
        assertTrue(csv.contains("Егор;Skoda Octavia;Комфорт;4,97;1580;14,00;да"));
        assertEquals(51, csv.split("\n").length);   // шапка, 3 тарифа, итог, 42 водителя
    }
}
