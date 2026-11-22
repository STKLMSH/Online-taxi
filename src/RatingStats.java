import java.util.List;

public class RatingStats {

    public static double average(List<Driver> drivers) {
        double sum = 0;
        for (Driver d : drivers) {
            sum += d.getRating();
        }
        return drivers.isEmpty() ? 0 : sum / drivers.size();
    }

    public static Driver best(List<Driver> drivers) {
        Driver best = null;
        for (Driver d : drivers) {
            if (best == null || d.getRating() > best.getRating()) {
                best = d;
            }
        }
        return best;
    }
}

