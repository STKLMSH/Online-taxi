import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

    void main() throws IOException {
        Properties config = new Properties();
        try (Reader reader = new InputStreamReader(
                new FileInputStream("resources/taxi.properties"), StandardCharsets.UTF_8)) {
            config.load(reader);
        }

        List<Driver> drivers = new ArrayList<>(List.of(
                new Driver("Андрей", "Kia Rio", "Эконом", 4, 1240),
                new Driver("Александр", "Hyundai Solaris", "Эконом", 3, 860),
                new Driver("Сергей", "Lada Vesta", "Эконом", 3, 430),
                new Driver("Егор", "Skoda Octavia", "Комфорт", 5, 1580),
                new Driver("Виталя", "Volkswagen Passat", "Комфорт", 4, 1320),
                new Driver("Максим", "Mercedes-Benz E-Class", "Бизнес", 2, 640)));

        System.out.println("Все водители:");
        for (Driver d : drivers) {
            System.out.println("  " + d);
        }

        System.out.printf("\n");
        System.out.printf("Средний рейтинг: %.2f%n", RatingStats.average(drivers));
        System.out.println("Лучший водитель: " + RatingStats.best(drivers));
    }
