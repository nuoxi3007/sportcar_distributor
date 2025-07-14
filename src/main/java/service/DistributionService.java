package service;

import model.Segment;
import model.SportCar;
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
}
