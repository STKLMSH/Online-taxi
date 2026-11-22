import java.util.ArrayList;
import java.util.List;

public class TariffFilter {

    public static List<Driver> byTariff(List<Driver> drivers, String tariff) {
        List<Driver> result = new ArrayList<>();
        for (Driver d : drivers) {
            if (d.getTariff().equalsIgnoreCase(tariff)) {
                result.add(d);
            }
        }
        return result;
    }
}

