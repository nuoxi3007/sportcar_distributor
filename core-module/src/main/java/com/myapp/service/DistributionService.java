package com.myapp.service;

import com.myapp.model.Segment;
import com.myapp.model.SportCar;
import java.util.List;
import java.util.Objects;

public class DistributionService {
    private static final double PREMIUM_MIN = 100_000.0;
    private static final double ELITE_MIN = 500_000.0;

    public void assignSegments(List<SportCar> cars) {
        Objects.requireNonNull(cars, "Car list cannot be null");

        for (SportCar car : cars) {
            Objects.requireNonNull(car, "Car cannot be null");

            double price = car.getPrice();
            Segment segment =
                    price < PREMIUM_MIN ? Segment.BUDGET :
                            price < ELITE_MIN ? Segment.PREMIUM :
                                    Segment.ELITE;

            car.setSegment(segment);
        }
    }

    public void printFullReport(List<SportCar> cars) {
        Objects.requireNonNull(cars, "Car list cannot be null");

        System.out.println("\n=== Полный отчёт по автопарку ===");
        System.out.printf("%-15s | %-15s | %-10s | %-12s | %-15s | %s%n",
                "Производитель", "Модель", "Класс", "Цена", "Л.с.", "Сегмент");
        System.out.println("-".repeat(85));

        cars.forEach(car -> System.out.printf("%-15s | %-15s | %-10s | $%,-10.2f | %-15d | %s%n",
                car.getManufacturer(),
                car.getModel(),
                car.getCarClass(),
                car.getPrice(),
                car.getHorsepower(),
                car.getSegment().getName()));
    }
}