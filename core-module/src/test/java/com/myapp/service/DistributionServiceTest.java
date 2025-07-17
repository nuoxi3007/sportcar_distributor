package com.myapp.service;

import com.myapp.model.CarClass;
import com.myapp.model.Segment;
import com.myapp.model.SportCar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class DistributionServiceTest {

    private DistributionService distributionService;
    private List<SportCar> testCars;
    private Locale originalLocale;

    @BeforeEach
    void setUp() {
        distributionService = new DistributionService();
        testCars = new ArrayList<>(Arrays.asList(
                new SportCar("BudgetCar", "ModelA", CarClass.SPORT, 95000.0, 300),     // Бюджетный
                new SportCar("PremiumCar1", "ModelB", CarClass.HYPERCAR, 120000.0, 600), // Премиум
                new SportCar("PremiumCar2", "ModelC", CarClass.SPORT, 450000.0, 550), // Премиум
                new SportCar("EliteCar", "ModelD", CarClass.TRACK, 700000.0, 800),     // Элитный
                new SportCar("BudgetCar2", "ModelE", CarClass.CLASSIC, 50000.0, 200)   // Бюджетный
        ));

        originalLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        Locale.setDefault(originalLocale);
    }


    @Test
    @DisplayName("Должен правильно назначать сегменты на основе цены")
    void assignSegments_shouldAssignCorrectSegments() {
        distributionService.assignSegments(testCars);

        assertEquals(Segment.BUDGET, testCars.get(0).getSegment()); // 95k
        assertEquals(Segment.PREMIUM, testCars.get(1).getSegment()); // 120k
        assertEquals(Segment.PREMIUM, testCars.get(2).getSegment()); // 450k
        assertEquals(Segment.ELITE, testCars.get(3).getSegment());   // 700k
        assertEquals(Segment.BUDGET, testCars.get(4).getSegment());  // 50k
    }

    @Test
    @DisplayName("Должен корректно обрабатывать пустой список при назначении сегментов")
    void assignSegments_shouldHandleEmptyList() {
        List<SportCar> emptyList = new ArrayList<>();
        assertDoesNotThrow(() -> distributionService.assignSegments(emptyList));
        assertTrue(emptyList.isEmpty());
    }

    @Test
    @DisplayName("Должен выводить полный отчет в консоль")
    void printFullReport_shouldPrintCorrectFormat() {
        distributionService.assignSegments(testCars);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        distributionService.printFullReport(testCars);

        System.setOut(originalOut);


        String expectedOutput = "=== Отчёт по автопарку ===\n" +
                "BudgetCar ModelA — BUDGET, $95,000.00, 300 л.с.\n" +
                "PremiumCar1 ModelB — PREMIUM, $120,000.00, 600 л.с.\n" +
                "PremiumCar2 ModelC — PREMIUM, $450,000.00, 550 л.с.\n" +
                "EliteCar ModelD — ELITE, $700,000.00, 800 л.с.\n" +
                "BudgetCar2 ModelE — BUDGET, $50,000.00, 200 л.с.\n";

        String actualOutput = outContent.toString().replace("\r\n", "\n");

        System.out.println("\n--- Ожидаемый вывод ---");
        System.out.print(expectedOutput.replace("\n", "[NL]\n"));
        System.out.println("Длина: " + expectedOutput.length());
        System.out.println("--- Фактический вывод ---");
        System.out.print(actualOutput.replace("\n", "[NL]\n"));
        System.out.println("Длина: " + actualOutput.length());

        assertEquals(expectedOutput, actualOutput, "Вывод отчета не соответствует ожидаемому.");
    }

    @Test
    @DisplayName("Должен выводить полный отчет корректно для пустого списка")
    void printFullReport_shouldHandleEmptyList() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        distributionService.printFullReport(new ArrayList<>());

        System.setOut(originalOut);

        assertEquals("=== Отчёт по автопарку ===\n", outContent.toString().replace("\r\n", "\n"));
    }
}