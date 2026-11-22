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
        return String.format("%-18s %-22s %-8s %5.2f %6d", name, car, tariff, rating, trips);
    }
}
