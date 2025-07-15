package com.myapp;

import model.CarClass;
import model.SportCar;
import model.Segment;
import service.CarFilterService;
import service.DistributionService;
import util.CarUtils;


import java.util.List;

/**
 * ЗАДАЧА:Разработать систему управления автопарком спорткаров дилерского центра
 * Функционал:
 * 1. Учет автомобилей с характеристиками:
 *    - Производитель и модель
 *    - Класс (SPORT, HYPERCAR, TRACK, CLASSIC)
 *    - Цена и мощность
 * 2. Автоматическое распределение по ценовым сегментам:
 *    - Бюджетный (до $100K)
 *    - Премиум ($100K-$500K)
 *    - Элитный (свыше $500K)
 * 3. Фильтрация по:
 *    - Классу автомобиля
 *    - Ценовому сегменту
 * 4. Формирование отчетов:
 *    - Полный список авто с сегментами
 *    - Отфильтрованные подборки
 */

public class Main {
    public static void main(String[] args) {
        List<SportCar> cars = CarUtils.generateRandomCars(10);

        DistributionService distributionService = new DistributionService();
        distributionService.assignSegments(cars);

        CarFilterService filterService = new CarFilterService();
        List<SportCar> sportCars = filterService.filterByClass(cars, CarClass.SPORT);

        System.out.println("Все спорткары с сегментами:");
        for (SportCar car : cars) {
            System.out.println(car);
        }

        System.out.println("\nПолный отчёт по автопарку:");
        distributionService.printFullReport(cars);

        System.out.println("\nФильтр: спорткары класса SPORT");
        for (SportCar car : sportCars) {
            System.out.printf("%s %s — Цена: $%,.2f, Л.с.: %d%n",
                    car.getManufacturer(), car.getModel(), car.getPrice(), car.getHorsepower());
        }

        System.out.println("\nФильтр: автомобили в диапазоне $50K – $200K");
        List<SportCar> filteredByPrice = filterService.filterByPriceRange(cars, 50000, 200000);
        for (SportCar car : filteredByPrice) {
            System.out.println(car);
        }


        System.out.println("\nФильтр: автомобили сегмента PREMIUM");
        List<SportCar> premiumCars = filterService.filterBySegment(cars, Segment.PREMIUM);
        for (SportCar car : premiumCars) {
            System.out.println(car);
        }


        System.out.println("\nФильтр: SPORT-класс и сегмент BUDGET");
        List<SportCar> sportBudgetCars = filterService.filterByClassAndSegment(cars, CarClass.SPORT, Segment.BUDGET);
        for (SportCar car : sportBudgetCars) {
            System.out.println(car);
        }


        CarUtils.sortByPrice(cars);
        System.out.println("\nОтсортировано по цене (возрастание):");
        for (SportCar car : cars) {
            System.out.printf("%s %s — $%,.2f%n", car.getManufacturer(), car.getModel(), car.getPrice());
        }


        CarUtils.sortByHorsepowerDesc(cars);
        System.out.println("\nОтсортировано по мощности (убывание):");
        for (SportCar car : cars) {
            System.out.printf("%s %s — %d л.с.%n", car.getManufacturer(), car.getModel(), car.getHorsepower());
        }


        if (cars.size() >= 2) {
            SportCar a = cars.get(0);
            SportCar b = cars.get(1);
            System.out.println("\nСравнение первых двух машин:");
            System.out.printf("По цене: %s дороже %s: %b%n",
                    a.getModel(), b.getModel(), CarUtils.compareByPrice(a, b) > 0);
            System.out.printf("По мощности: %s мощнее %s: %b%n",
                    a.getModel(), b.getModel(), CarUtils.compareByHorsepower(a, b) > 0);
        }
    }
}
