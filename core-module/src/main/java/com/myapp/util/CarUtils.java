package com.myapp.util;

import com.myapp.model.SportCar;
import com.myapp.model.CarClass;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CarUtils {
    private static final String[] MANUFACTURERS = {"Ferrari", "Porsche", "Lamborghini", "McLaren"};
    private static final String[][] MODELS = {
            {"488", "F8", "SF90"},
            {"911", "Taycan", "Panamera"},
            {"Aventador", "Huracan", "Urus"},
            {"720S", "Artura", "P1"}
    };
    private static final Random random = new Random();

    public static List<SportCar> generateRandomCars(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> {
                    int brand = random.nextInt(MANUFACTURERS.length);
                    return new SportCar(
                            MANUFACTURERS[brand],
                            MODELS[brand][random.nextInt(MODELS[brand].length)],
                            CarClass.values()[random.nextInt(CarClass.values().length)],
                            30000 + random.nextDouble() * 500000,
                            300 + random.nextInt(700)
                    );
                })
                .collect(Collectors.toList());
    }

    public static void sortByPrice(List<SportCar> cars) {
        cars.sort(Comparator.comparingDouble(SportCar::getPrice));
    }

    public static void sortByHorsepowerDesc(List<SportCar> cars) {
        cars.sort((a, b) -> Integer.compare(b.getHorsepower(), a.getHorsepower()));
    }

    public static int compareByPrice(SportCar a, SportCar b) {
        return Double.compare(a.getPrice(), b.getPrice());
    }

    public static int compareByHorsepower(SportCar a, SportCar b) {
        return Integer.compare(a.getHorsepower(), b.getHorsepower());
    }
}