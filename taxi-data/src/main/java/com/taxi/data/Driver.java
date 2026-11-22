package com.taxi.data;

public class Driver {
    private String name;
    private String car;
    private String tariff;
    private double rating;
    private int trips;
    private double earnings;     // сумма за все поездки
    private boolean available;   // свободен ли сейчас

    public String getName() { return name; }
    public String getCar() { return car; }
    public String getTariff() { return tariff; }
    public double getRating() { return rating; }
    public int getTrips() { return trips; }
    public double getEarnings() { return earnings; }
    public boolean isAvailable() { return available; }

    /** Средняя стоимость одной поездки у этого водителя. */
    public double getAverageFare() {
        return trips == 0 ? 0 : earnings / trips;
    }
}
