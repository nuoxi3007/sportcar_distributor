package com.myapp.service;

import com.myapp.model.CarClass;
import com.myapp.model.SportCar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarReportServiceTest {

    @Mock
    private DistributionService distributionService;

    @Mock
    private CarFilterService carFilterService;

    @InjectMocks
    private CarReportService carReportService;

    private SportCar createTestCar(String manufacturer, String model, CarClass carClass,
                                   double price, int horsepower) {
        return new SportCar(manufacturer, model, carClass, price, horsepower);
    }

    @Test
    void testGenerateReportWithValidCars() {
        SportCar sportCar = createTestCar("Porsche", "911 GT3", CarClass.SPORT, 160_000, 502);
        SportCar hyperCar = createTestCar("Koenigsegg", "Jesko", CarClass.HYPERCAR, 3_000_000, 1600);
        SportCar luxuryCar = createTestCar("Bentley", "Continental GT", CarClass.LUXURY, 220_000, 542);

        List<SportCar> allCars = List.of(sportCar, hyperCar, luxuryCar);
        List<SportCar> expectedFilteredCars = List.of(sportCar, luxuryCar);

        when(carFilterService.filterByPriceRange(anyList(), eq(100_000.0), eq(500_000.0)))
                .thenReturn(expectedFilteredCars);

        List<SportCar> result = carReportService.generateReport(allCars);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size()),
                () -> assertFalse(result.contains(hyperCar)),
                () -> verify(distributionService, times(1)).assignSegments(allCars),
                () -> verify(carFilterService, times(1)).filterByPriceRange(allCars, 100_000.0, 500_000.0)
        );
    }

    @Test
    void testGenerateReportWithEmptyList() {
        List<SportCar> emptyList = Collections.emptyList();

        List<SportCar> result = carReportService.generateReport(emptyList);

        assertAll(
                () -> assertNotNull(result),
                () -> assertTrue(result.isEmpty()),

                () -> verify(distributionService, never()).assignSegments(anyList()),
                () -> verify(carFilterService, never()).filterByPriceRange(anyList(), anyDouble(), anyDouble())
        );
    }

    @Test
    void testGenerateReportWithNullInput() {

        assertThrows(NullPointerException.class,
                () -> carReportService.generateReport(null));
        verifyNoInteractions(distributionService, carFilterService);
    }

    @Test
    void testSegmentAssignmentInReport() {
        SportCar testCar = createTestCar("Audi", "R8", CarClass.SPORT, 150_000, 562);
        List<SportCar> cars = List.of(testCar);

        when(carFilterService.filterByPriceRange(anyList(), eq(100_000.0), eq(500_000.0)))
                .thenReturn(cars);

        carReportService.generateReport(cars);

        verify(distributionService).assignSegments(cars);
    }

    @Test
    void testPriceFilteringLogic() {
        SportCar cheapCar = createTestCar("Toyota", "Supra", CarClass.SPORT, 55_000, 335);
        SportCar expensiveCar = createTestCar("Lamborghini", "Aventador", CarClass.HYPERCAR, 500_000, 770);
        List<SportCar> cars = List.of(cheapCar, expensiveCar);

        when(carFilterService.filterByPriceRange(anyList(), eq(100_000.0), eq(500_000.0)))
                .thenReturn(List.of(expensiveCar));

        List<SportCar> result = carReportService.generateReport(cars);

        assertAll(
                () -> assertEquals(1, result.size()),
                () -> assertTrue(result.contains(expensiveCar)),
                () -> assertFalse(result.contains(cheapCar))
        );
    }
}