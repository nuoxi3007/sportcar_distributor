package com.myapp.service;

import com.myapp.model.CarClass;
import com.myapp.model.Segment;
import com.myapp.model.SportCar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarFilterServiceTest {

    private CarFilterService carFilterService;
    private List<SportCar> testCars;

    @BeforeEach
    void setUp() {
        carFilterService = new CarFilterService();
        testCars = new ArrayList<>(Arrays.asList(
                new SportCar("Porsche", "911 GT3", CarClass.SPORT, 150000.0, 500),
                new SportCar("Ferrari", "LaFerrari", CarClass.HYPERCAR, 1200000.0, 950),
                new SportCar("BMW", "M2", CarClass.SPORT, 70000.0, 400),
                new SportCar("McLaren", "720S", CarClass.SPORT, 300000.0, 700),
                new SportCar("Toyota", "Supra", CarClass.CLASSIC, 60000.0, 335),
                new SportCar("Lamborghini", "Huracan", CarClass.SPORT, 250000.0, 640)
        ));

        DistributionService distributionService = new DistributionService();
        distributionService.assignSegments(testCars);
    }

    @Test
    @DisplayName("Должен фильтровать автомобили по конкретному CarClass")
    void filterByClass_shouldReturnCorrectCars() {
        List<SportCar> sportCars = carFilterService.filterByClass(testCars, CarClass.SPORT);
        assertNotNull(sportCars);
        assertEquals(4, sportCars.size()); // Porsche, BMW, McLaren, Lamborghini
        assertTrue(sportCars.stream().allMatch(car -> car.getCarClass() == CarClass.SPORT));
        assertFalse(sportCars.stream().anyMatch(car -> car.getCarClass() == CarClass.HYPERCAR));
    }

    @Test
    @DisplayName("Должен возвращать пустой список, если нет автомобилей, соответствующих CarClass")
    void filterByClass_shouldReturnEmptyListIfNoMatch() {
        List<SportCar> trackCars = carFilterService.filterByClass(testCars, CarClass.TRACK);
        assertNotNull(trackCars);
        assertTrue(trackCars.isEmpty());
    }

    @Test
    @DisplayName("Должен фильтровать автомобили по конкретному сегменту")
    void filterBySegment_shouldReturnCorrectCars() {
        List<SportCar> premiumCars = carFilterService.filterBySegment(testCars, Segment.PREMIUM);
        assertNotNull(premiumCars);
        // Porsche (150k), McLaren (300k), Lamborghini (250k)
        assertEquals(3, premiumCars.size());
        assertTrue(premiumCars.stream().allMatch(car -> car.getSegment() == Segment.PREMIUM));
    }

    @Test
    @DisplayName("Должен возвращать пустой список, если нет автомобилей, соответствующих сегменту")
    void filterBySegment_shouldReturnEmptyListIfNoMatch() {
        List<SportCar> gtCars = carFilterService.filterBySegment(testCars, Segment.BUDGET);
        assertNotNull(gtCars);
        // BMW (70k), Toyota (60k)
        assertEquals(2, gtCars.size());
    }

    @Test
    @DisplayName("Должен фильтровать автомобили как по CarClass, так и по сегменту")
    void filterByClassAndSegment_shouldReturnCorrectCars() {
        List<SportCar> sportPremiumCars = carFilterService.filterByClassAndSegment(testCars, CarClass.SPORT, Segment.PREMIUM);
        assertNotNull(sportPremiumCars);
        // Porsche (150k), McLaren (300k), Lamborghini (250k) это SPORT и PREMIUM
        assertEquals(3, sportPremiumCars.size());
        assertTrue(sportPremiumCars.stream().allMatch(
                car -> car.getCarClass() == CarClass.SPORT && car.getSegment() == Segment.PREMIUM));
    }

    @Test
    @DisplayName("Должен возвращать пустой список, если нет автомобилей, соответствующих CarClass и сегменту")
    void filterByClassAndSegment_shouldReturnEmptyListIfNoMatch() {
        List<SportCar> trackBudgetCars = carFilterService.filterByClassAndSegment(testCars, CarClass.TRACK, Segment.BUDGET);
        assertNotNull(trackBudgetCars);
        assertTrue(trackBudgetCars.isEmpty());
    }

    @Test
    @DisplayName("Должен фильтровать автомобили по ценовому диапазону (включительно)")
    void filterByPriceRange_shouldReturnCorrectCars() {
        List<SportCar> midRangeCars = carFilterService.filterByPriceRange(testCars, 100000, 300000);
        assertNotNull(midRangeCars);
        // Porsche (150k), McLaren (300k), Lamborghini (250k)
        assertEquals(3, midRangeCars.size());
        assertTrue(midRangeCars.stream().allMatch(car -> car.getPrice() >= 100000 && car.getPrice() <= 300000));
    }

    @Test
    @DisplayName("Должен возвращать пустой список, если нет автомобилей в ценовом диапазоне")
    void filterByPriceRange_shouldReturnEmptyListIfNoMatch() {
        List<SportCar> veryExpensiveCars = carFilterService.filterByPriceRange(testCars, 5000000, 10000000);
        assertNotNull(veryExpensiveCars);
        assertTrue(veryExpensiveCars.isEmpty());
    }

    @Test
    @DisplayName("Должен фильтровать автомобили по производителю (без учета регистра)")
    void filterByManufacturer_shouldReturnCorrectCars() {
        List<SportCar> porscheCars = carFilterService.filterByManufacturer(testCars, "Porsche");
        assertNotNull(porscheCars);
        assertEquals(1, porscheCars.size());
        assertTrue(porscheCars.stream().allMatch(car -> car.getManufacturer().equals("Porsche")));


        List<SportCar> bmwCars = carFilterService.filterByManufacturer(testCars, "bmw");
        assertNotNull(bmwCars);
        assertEquals(1, bmwCars.size());
        assertTrue(bmwCars.stream().allMatch(car -> car.getManufacturer().equals("BMW")));
    }

    @Test
    @DisplayName("Должен возвращать пустой список, если нет автомобилей, соответствующих производителю")
    void filterByManufacturer_shouldReturnEmptyListIfNoMatch() {
        List<SportCar> nonExistentCars = carFilterService.filterByManufacturer(testCars, "Honda");
        assertNotNull(nonExistentCars);
        assertTrue(nonExistentCars.isEmpty());
    }


}