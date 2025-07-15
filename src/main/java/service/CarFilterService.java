package service;

import model.CarClass;
import model.Segment;
import model.SportCar;
import java.util.List;
import java.util.stream.Collectors;

public class CarFilterService {

    public List<SportCar> filterByClass(List<SportCar> cars, CarClass carClass) {
        return cars.stream()
                .filter(car -> car.getCarClass() == carClass)
                .collect(Collectors.toList());
    }

    public List<SportCar> filterBySegment(List<SportCar> cars, Segment segment) {
        return cars.stream()
                .filter(car -> car.getSegment() == segment)
                .collect(Collectors.toList());
    }

    public List<SportCar> filterByClassAndSegment(List<SportCar> cars, CarClass carClass, Segment segment) {
        return cars.stream()
                .filter(car -> car.getCarClass() == carClass && car.getSegment() == segment)
                .collect(Collectors.toList());
    }


    public List<SportCar> filterByPriceRange(List<SportCar> cars, double minPrice, double maxPrice) {
        return cars.stream()
                .filter(car -> car.getPrice() >= minPrice && car.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
}
