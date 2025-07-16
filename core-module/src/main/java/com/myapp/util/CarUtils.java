package com.myapp.util;

import com.myapp.model.CarClass;
import com.myapp.model.SportCar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class CarUtils {

    private static final Random random = new Random();

    private static final String[] BRANDS = {
            "Porsche", "Ferrari", "BMW", "McLaren", "Toyota", "Lamborghini", "Audi"
    };

    private static final String[] MODELS = {
            "911 GT3", "LaFerrari", "M2", "720S", "Supra", "Huracan", "R8"
    };

    /**
     * Генерирует список случайных спорткаров.
     * @param count количество авто для генерации
     * @return список спорткаров
     */
    public static List<SportCar> generateRandomCars(int count) {
        List<SportCar> cars = new ArrayList<>();
        CarClass[] classes = CarClass.values();

        for (int i = 0; i < count; i++) {
            String brand = BRANDS[random.nextInt(BRANDS.length)];
            String model = MODELS[random.nextInt(MODELS.length)];
            CarClass carClass = classes[random.nextInt(classes.length)];

            double price = 30_000 + random.nextDouble() * 1_970_000;

            int horsepower = 200 + random.nextInt(800);

            cars.add(new SportCar(brand, model, carClass, price, horsepower));
        }

        return cars;
    }

    public static void sortByPrice(List<SportCar> cars) {
        cars.sort(Comparator.comparingDouble(SportCar::getPrice));
    }


    public static void sortByHorsepowerDesc(List<SportCar> cars) {
        cars.sort(Comparator.comparingInt(SportCar::getHorsepower).reversed());
    }


    public static int compareByPrice(SportCar car1, SportCar car2) {
        return Double.compare(car1.getPrice(), car2.getPrice());
    }


    public static int compareByHorsepower(SportCar car1, SportCar car2) {
        return Integer.compare(car1.getHorsepower(), car2.getHorsepower());
    }
}