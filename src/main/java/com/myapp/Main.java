package com.myapp;

import model.CarClass;
import model.SportCar;
import model.Segment;
import service.CarFilterService;
import service.DistributionService;

import java.util.List;

/**
 * ЗАДАЧА:  Разработать систему  управления автопарком спорткаров дилерского центра
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
        SportCar car1 = new SportCar("Ferrari", "488 GTB", CarClass.SPORT, 250000, 660);
        SportCar car2 = new SportCar("Lamborghini", "Aventador", CarClass.HYPERCAR, 500000, 700);
        SportCar car3 = new SportCar("Toyota", "Supra", CarClass.SPORT, 60000, 335);
        SportCar car4 = new SportCar("Porsche", "911 GT3", CarClass.TRACK, 150000, 500);
        SportCar car5 = new SportCar("Ford", "Mustang", CarClass.CLASSIC, 55000, 450);

        List<SportCar> cars = List.of(car1, car2, car3, car4, car5);

        DistributionService distributionService = new DistributionService();
        distributionService.assignSegments(cars);

        CarFilterService filterService = new CarFilterService();
        List<SportCar> sportCars = filterService.filterByClass(cars, CarClass.SPORT);


        System.out.println("Все спорткары с сегментами:");
        for (SportCar car : cars) {
            System.out.println(car.toString());
        }


        System.out.println("\nФильтр: спорткары класса SPORT");
        for (SportCar car : sportCars) {
            System.out.printf("%s %s — Цена: $%,.2f, Л.с.: %d%n",
                    car.getManufacturer(), car.getModel(), car.getPrice(), car.getHorsepower());
        }
    }
}