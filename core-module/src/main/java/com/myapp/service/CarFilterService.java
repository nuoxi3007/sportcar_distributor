package com.myapp.service;

import com.myapp.model.CarClass;
import com.myapp.model.Segment;
import com.myapp.model.SportCar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CarFilterService {
    public List<SportCar> filterByClass(List<SportCar> cars, CarClass carClass) {
        return filter(cars, car -> car.getCarClass() == carClass);
    }

    public List<SportCar> filterBySegment(List<SportCar> cars, Segment segment) {
        return filter(cars, car -> car.getSegment() == segment);
    }

    public List<SportCar> filterByClassAndSegment(List<SportCar> cars, CarClass carClass, Segment segment) {
        return filter(cars, car -> car.getCarClass() == carClass && car.getSegment() == segment);
    }

    public List<SportCar> filterByPriceRange(List<SportCar> cars, double minPrice, double maxPrice) {
        return filter(cars, car -> car.getPrice() >= minPrice && car.getPrice() <= maxPrice);
    }

    public List<SportCar> filterByManufacturer(List<SportCar> cars, String manufacturer) {
        if (manufacturer == null || manufacturer.trim().isEmpty()) {
            return List.of();
        }
        return filter(cars, car -> manufacturer.equalsIgnoreCase(car.getManufacturer()));
    }

    private List<SportCar> filter(List<SportCar> cars, java.util.function.Predicate<SportCar> predicate) {
        if (cars == null) {
            return List.of();
        }
        return cars.stream()
                .filter(Objects::nonNull)
                .filter(predicate)
                .collect(Collectors.toList());
    }
}