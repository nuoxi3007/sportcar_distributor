package com.myapp.service;

import com.myapp.model.CarClass;
import com.myapp.model.Segment;
import com.myapp.model.SportCar;
import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class DistributionServiceTest {
    private DistributionService distributionService;
    private List<SportCar> testCars;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;
    private Locale originalLocale;

    @BeforeEach
    void setUp() {
        distributionService = new DistributionService();
        testCars = List.of(
                new SportCar("BudgetCar", "ModelA", CarClass.SPORT, 95_000.0, 300),
                new SportCar("PremiumCar1", "ModelB", CarClass.HYPERCAR, 120_000.0, 600),
                new SportCar("PremiumCar2", "ModelC", CarClass.SPORT, 450_000.0, 550),
                new SportCar("EliteCar", "ModelD", CarClass.TRACK, 700_000.0, 800),
                new SportCar("BudgetCar2", "ModelE", CarClass.CLASSIC, 50_000.0, 200)
        );

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        originalLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        Locale.setDefault(originalLocale);
    }

    @Test
    @DisplayName("Должен правильно назначать сегменты на основе цены")
    void assignSegments_shouldAssignCorrectSegments() {
        distributionService.assignSegments(testCars);

        assertAll(
                () -> assertEquals(Segment.BUDGET, testCars.get(0).getSegment()),
                () -> assertEquals(Segment.PREMIUM, testCars.get(1).getSegment()),
                () -> assertEquals(Segment.PREMIUM, testCars.get(2).getSegment()),
                () -> assertEquals(Segment.ELITE, testCars.get(3).getSegment()),
                () -> assertEquals(Segment.BUDGET, testCars.get(4).getSegment())
        );
    }

    @Test
    @DisplayName("Должен корректно обрабатывать пустой список")
    void assignSegments_shouldHandleEmptyList() {
        List<SportCar> emptyList = new ArrayList<>();
        assertDoesNotThrow(() -> distributionService.assignSegments(emptyList));
    }

    @Test
    @DisplayName("Должен выводить полный отчет в правильном формате")
    void printFullReport_shouldPrintCorrectFormat() {
        distributionService.assignSegments(testCars);
        String expectedOutput = """
            === Отчёт по автопарку ===
            BudgetCar ModelA — BUDGET, $95,000.00, 300 л.с.
            PremiumCar1 ModelB — PREMIUM, $120,000.00, 600 л.с.
            PremiumCar2 ModelC — PREMIUM, $450,000.00, 550 л.с.
            EliteCar ModelD — ELITE, $700,000.00, 800 л.с.
            BudgetCar2 ModelE — BUDGET, $50,000.00, 200 л.с.
            """;

        distributionService.printFullReport(testCars);
        String actualOutput = outContent.toString().replace("\r\n", "\n");

        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    @DisplayName("Должен выводить заголовок для пустого списка")
    void printFullReport_shouldPrintHeaderForEmptyList() {
        String expected = "=== Отчёт по автопарку ===\n";
        distributionService.printFullReport(List.of());
        String actual = outContent.toString().replace("\r\n", "\n");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Должен корректно назначать сегмент для граничных значений цен")
    void assignSegments_shouldHandleBoundaryPrices() {
        List<SportCar> boundaryCars = List.of(
                new SportCar("Car1", "Model", CarClass.SPORT, 100_000.0, 300),
                new SportCar("Car2", "Model", CarClass.SPORT, 500_000.0, 400)
        );

        distributionService.assignSegments(boundaryCars);

        assertAll(
                () -> assertEquals(Segment.PREMIUM, boundaryCars.get(0).getSegment()),
                () -> assertEquals(Segment.ELITE, boundaryCars.get(1).getSegment())
        );
    }
}