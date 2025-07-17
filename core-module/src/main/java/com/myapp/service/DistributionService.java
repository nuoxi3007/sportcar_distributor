package com.myapp.service;

import com.myapp.model.Segment;
import com.myapp.model.SportCar;
import java.util.List;

public class DistributionService {
    private static final double PREMIUM_MIN = 100_000.0;
    private static final double ELITE_MIN = 500_000.0;

    public void assignSegments(List<SportCar> cars) {
        for (SportCar car : cars) {
            double price = car.getPrice();

            if (price < PREMIUM_MIN) {
                car.setSegment(Segment.BUDGET);
            } else if (price < ELITE_MIN) {
                car.setSegment(Segment.PREMIUM);
            } else {
                car.setSegment(Segment.ELITE);
            }
        }
    }

    public void printFullReport(List<SportCar> cars) {
        System.out.println("=== Отчёт по автопарку ===");
        for (SportCar car : cars) {
            System.out.printf("%s %s — %s, $%,.2f, %d л.с.%n",
                    car.getManufacturer(),
                    car.getModel(),
                    car.getSegment(),
                    car.getPrice(),
                    car.getHorsepower());
        }
    }
}