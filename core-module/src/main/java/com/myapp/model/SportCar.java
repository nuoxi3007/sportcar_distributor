package com.myapp.model;

public class SportCar {
    private String manufacturer;
    private String model;
    private CarClass carClass;
    private double price;
    private int horsepower;
    private Segment segment;


    public SportCar(String manufacturer, String model, CarClass carClass, double price, int horsepower) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.carClass = carClass;
        this.price = price;
        this.horsepower = horsepower;
    }


    public String toShortString() {
        return String.format("%s %s — $%,.2f (%s)", manufacturer, model, price, segment);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    public double getPrice() {
        return price;
    }

    public int getHorsepower() {
        return horsepower;
    }

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    @Override
    public String toString() {
        return String.format("%s %s — Класс: %s, Цена: $%,.2f, Л.с.: %d, Сегмент: %s",
                manufacturer, model, carClass, price, horsepower,
                segment != null ? segment.getName() : "не назначен");
    }
}