package com.myapp.model;

import java.util.Objects;

public class SportCar {
    private final String manufacturer;
    private final String model;
    private final CarClass carClass;
    private final double price;
    private final int horsepower;
    private Segment segment;

    public SportCar(String manufacturer, String model, CarClass carClass, double price, int horsepower) {
        this.manufacturer = Objects.requireNonNull(manufacturer, "Manufacturer cannot be null");
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.carClass = Objects.requireNonNull(carClass, "CarClass cannot be null");
        this.price = validatePrice(price);
        this.horsepower = validateHorsepower(horsepower);
    }

    private double validatePrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return price;
    }

    private int validateHorsepower(int horsepower) {
        if (horsepower <= 0) {
            throw new IllegalArgumentException("Horsepower must be positive");
        }
        return horsepower;
    }


    public String getManufacturer() { return manufacturer; }
    public String getModel() { return model; }
    public CarClass getCarClass() { return carClass; }
    public double getPrice() { return price; }
    public int getHorsepower() { return horsepower; }
    public Segment getSegment() { return segment; }


    public void setSegment(Segment segment) {
        this.segment = Objects.requireNonNull(segment, "Segment cannot be null");
    }

    @Override
    public String toString() {
        return String.format("%s %s — Класс: %s, Цена: $%,.2f, Л.с.: %d, Сегмент: %s",
                manufacturer, model, carClass, price, horsepower,
                segment != null ? segment.getName() : "не назначен");
    }

    public String toShortString() {
        return String.format("%s %s — $%,.2f (%s)", manufacturer, model, price,
                segment != null ? segment.getName() : "нет сегмента");
    }
}