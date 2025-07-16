package com.myapp.service;

import com.myapp.model.Segment;
import com.myapp.model.SportCar;
import java.util.List;

public class DistributionService {

    public void assignSegments(List<SportCar> cars) {
        for (SportCar car : cars) {
            double price = car.getPrice();

            if (price <= 100_000) {
                car.setSegment(Segment.BUDGET);
            } else if (price <= 500_000) {
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
                    car.getManufacturer(), car.getModel(),
                    car.getSegment(), car.getPrice(), car.getHorsepower());
        }
    }

}
