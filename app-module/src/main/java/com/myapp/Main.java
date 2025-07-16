package com.myapp;

import com.myapp.model.CarClass;
import com.myapp.model.SportCar;
import com.myapp.model.Segment;
import com.myapp.service.CarFilterService;
import com.myapp.service.DistributionService;
import com.myapp.util.CarUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Генерация списка спорткаров...");
        List<SportCar> cars = CarUtils.generateRandomCars(10);
        logger.info("Сгенерировано {} автомобилей.", cars.size());

        DistributionService distributionService = new DistributionService();
        distributionService.assignSegments(cars);
        logger.info("Автомобили распределены по ценовым сегментам.");

        CarFilterService filterService = new CarFilterService();
        List<SportCar> sportCars = filterService.filterByClass(cars, CarClass.SPORT);
        logger.info("Отфильтрованы спорткары класса SPORT, найдено {} шт.", sportCars.size());

        System.out.println("Все спорткары с сегментами:");
        for (SportCar car : cars) {
            System.out.println(car);
        }

        System.out.println("\nПолный отчёт по автопарку:");
        distributionService.printFullReport(cars);

        logger.info("Вывод отчёта и фильтров на экран.");

        System.out.println("\nФильтр: спорткары класса SPORT");
        for (SportCar car : sportCars) {
            System.out.printf("%s %s — Цена: $%,.2f, Л.с.: %d%n",
                    car.getManufacturer(), car.getModel(), car.getPrice(), car.getHorsepower());
        }

        System.out.println("\nФильтр: автомобили в диапазоне $50K – $200K");
        List<SportCar> filteredByPrice = filterService.filterByPriceRange(cars, 50000, 200000);
        logger.info("Фильтр по цене от 50K до 200K: найдено {} автомобилей.", filteredByPrice.size());
        for (SportCar car : filteredByPrice) {
            System.out.println(car);
        }

        System.out.println("\nФильтр: автомобили сегмента PREMIUM");
        List<SportCar> premiumCars = filterService.filterBySegment(cars, Segment.PREMIUM);
        logger.info("Фильтр по сегменту PREMIUM: найдено {} автомобилей.", premiumCars.size());
        for (SportCar car : premiumCars) {
            System.out.println(car);
        }

        System.out.println("\nФильтр: SPORT-класс и сегмент BUDGET");
        List<SportCar> sportBudgetCars = filterService.filterByClassAndSegment(cars, CarClass.SPORT, Segment.BUDGET);
        logger.info("Фильтр по классу SPORT и сегменту BUDGET: найдено {} автомобилей.", sportBudgetCars.size());
        for (SportCar car : sportBudgetCars) {
            System.out.println(car);
        }

        CarUtils.sortByPrice(cars);
        logger.info("Отсортировано по цене (возрастание).");
        System.out.println("\nОтсортировано по цене (возрастание):");
        for (SportCar car : cars) {
            System.out.printf("%s %s — $%,.2f%n", car.getManufacturer(), car.getModel(), car.getPrice());
        }

        CarUtils.sortByHorsepowerDesc(cars);
        logger.info("Отсортировано по мощности (убывание).");
        System.out.println("\nОтсортировано по мощности (убывание):");
        for (SportCar car : cars) {
            System.out.printf("%s %s — %d л.с.%n", car.getManufacturer(), car.getModel(), car.getHorsepower());
        }

        if (cars.size() >= 2) {
            SportCar a = cars.get(0);
            SportCar b = cars.get(1);
            logger.info("Сравнение первых двух автомобилей: {} и {}", a.getModel(), b.getModel());
            System.out.println("\nСравнение первых двух машин:");
            System.out.printf("По цене: %s дороже %s: %b%n",
                    a.getModel(), b.getModel(), CarUtils.compareByPrice(a, b) > 0);
            System.out.printf("По мощности: %s мощнее %s: %b%n",
                    a.getModel(), b.getModel(), CarUtils.compareByHorsepower(a, b) > 0);
        }

        logger.info("Программа завершена.");
    }
}
