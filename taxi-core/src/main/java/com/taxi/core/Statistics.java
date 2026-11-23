package com.taxi.core;

import com.taxi.data.Driver;

import java.util.Comparator;
import java.util.List;

public class Statistics {

    private final int count;
    private final long available;
    private final double averageRating;
    private final double minRating;
    private final double maxRating;
    private final long totalTrips;
    private final double averageFare;
    private final Driver best;

    public Statistics(List<Driver> drivers) {
        count = drivers.size();
        available = drivers.stream().filter(Driver::isAvailable).count();
        averageRating = drivers.stream().mapToDouble(Driver::getRating).average().orElse(0);
        minRating = drivers.stream().mapToDouble(Driver::getRating).min().orElse(0);
        maxRating = drivers.stream().mapToDouble(Driver::getRating).max().orElse(0);
        totalTrips = drivers.stream().mapToLong(Driver::getTrips).sum();
        double earnings = drivers.stream().mapToDouble(Driver::getEarnings).sum();
        averageFare = totalTrips == 0 ? 0 : earnings / totalTrips;   // взвешено по числу поездок
        best = drivers.stream().max(Comparator.comparingDouble(Driver::getRating)).orElse(null);
    }

    public int getCount() { return count; }
    public long getAvailable() { return available; }
    public double getAverageRating() { return averageRating; }
    public double getMinRating() { return minRating; }
    public double getMaxRating() { return maxRating; }
    public long getTotalTrips() { return totalTrips; }
    public double getAverageFare() { return averageFare; }
    public Driver getBest() { return best; }
}
