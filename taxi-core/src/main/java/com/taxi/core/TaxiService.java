package com.taxi.core;

import com.taxi.data.Driver;
import com.taxi.data.TaxiData;
import com.taxi.data.TaxiRepository;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class TaxiService {

    private final TaxiData data;

    public TaxiService(TaxiData data) {
        this.data = data;
    }

    /** Сервис с данными из taxi.json */
    public static TaxiService fromJson() {
        return new TaxiService(TaxiRepository.load());
    }

    public String getServiceName() { return data.getService(); }
    public String getCurrency() { return data.getCurrency(); }
    public List<Driver> getAllDrivers() { return data.getDrivers(); }

    /** Тарифы в порядке появления в файле: Эконом, Комфорт, Бизнес */
    public List<String> getTariffs() {
        return data.getDrivers().stream().map(Driver::getTariff).distinct().toList();
    }

    /**
     * Фильтр по тарифу и поиск по имени или марке авто
     * Пустой тариф или пустой запрос — условие не применяется
     */
    public List<Driver> search(String tariff, String query, boolean onlyAvailable) {
        String q = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        return data.getDrivers().stream()
                .filter(d -> tariff == null || tariff.isBlank() || d.getTariff().equals(tariff))
                .filter(d -> q.isEmpty()
                        || d.getName().toLowerCase(Locale.ROOT).contains(q)
                        || d.getCar().toLowerCase(Locale.ROOT).contains(q))
                .filter(d -> !onlyAvailable || d.isAvailable())
                .sorted(Comparator.comparingDouble(Driver::getRating).reversed())
                .toList();
    }

    public Statistics statistics(List<Driver> drivers) {
        return new Statistics(drivers);
    }

    /** Отчёт по тарифам: для каждого тарифа своя статистика */
    public Map<String, Statistics> tariffReport() {
        Map<String, Statistics> report = new LinkedHashMap<>();
        for (String tariff : getTariffs()) {
            report.put(tariff, new Statistics(search(tariff, null, false)));
        }
        return report;
    }

    /** Статистика по рейтингу: сколько водителей в каждой группе */
    public Map<String, Long> ratingGroups() {
        Map<String, Long> groups = new LinkedHashMap<>();
        groups.put("4.5 – 5.0", count(4.5, 5.01));
        groups.put("4.0 – 4.5", count(4.0, 4.5));
        groups.put("3.0 – 4.0", count(3.0, 4.0));
        groups.put("ниже 3.0", count(0, 3.0));
        return groups;
    }

    private long count(double from, double to) {
        return data.getDrivers().stream()
                .filter(d -> d.getRating() >= from && d.getRating() < to)
                .count();
    }

    /** Отчёт в CSV */
    public String toCsv() {
        StringBuilder csv = new StringBuilder();
        csv.append(row("Отчёт", getServiceName())).append('\n');

        csv.append(row("Тариф", "Водителей", "Свободно", "Средний рейтинг", "Поездок",
                "Средняя стоимость, " + getCurrency()));
        tariffReport().forEach((tariff, s) -> csv.append(statsRow(tariff, s)));
        csv.append(statsRow("Итого", statistics(getAllDrivers()))).append('\n');

        csv.append(row("Водитель", "Автомобиль", "Тариф", "Рейтинг", "Поездок",
                "Средняя стоимость", "Свободен"));
        for (Driver d : search(null, null, false)) {
            csv.append(row(d.getName(), d.getCar(), d.getTariff(), num(d.getRating()), d.getTrips(),
                    num(d.getAverageFare()), d.isAvailable() ? "да" : "нет"));
        }
        return csv.toString();
    }

    private static String statsRow(String name, Statistics s) {
        return row(name, s.getCount(), s.getAvailable(), num(s.getAverageRating()),
                s.getTotalTrips(), num(s.getAverageFare()));
    }

    /** Одна строка CSV: значения через «;» */
    private static String row(Object... cells) {
        return Arrays.stream(cells).map(String::valueOf).collect(Collectors.joining(";")) + "\n";
    }

    private static String num(double value) {
        return String.format(Locale.forLanguageTag("ru"), "%.2f", value);
    }
}
