package com.myapp.service;

import com.myapp.model.SportCar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CarReportService {
    private final DistributionService distributionService;
    private final CarFilterService carFilterService;

    public CarReportService(DistributionService distributionService,
                            CarFilterService carFilterService) {
        this.distributionService = Objects.requireNonNull(distributionService,
                "DistributionService cannot be null");
        this.carFilterService = Objects.requireNonNull(carFilterService,
                "CarFilterService cannot be null");
    }

    public List<SportCar> generateReport(List<SportCar> cars) {
        Objects.requireNonNull(cars, "Car list cannot be null");

        if (cars.isEmpty()) {
            return Collections.emptyList();
        }

        if (cars.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Car list contains null elements");
        }

        distributionService.assignSegments(cars);
        return carFilterService.filterByPriceRange(cars, 100_000, 500_000);
    }
}