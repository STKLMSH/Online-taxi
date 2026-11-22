public class Driver {

    private final String name;
    private final String car;
    private final String tariff;
    private final double rating;
    private final int trips;

    public Driver(String name, String car, String tariff, double rating, int trips) {
        this.name = name;
        this.car = car;
        this.tariff = tariff;
        this.rating = rating;
        this.trips = trips;
    }

    public String getName() {
        return name;
    }

    public String getCar() {
        return car;
    }

    public String getTariff() {
        return tariff;
    }

    public double getRating() {
        return rating;
    }

    public int getTrips() {
        return trips;
    }

    @Override
    public String toString() {
        return name + " (" + car + ", " + tariff + "), рейтинг "
                + String.format("%.2f", rating) + ", поездок: " + trips;
    }
}
