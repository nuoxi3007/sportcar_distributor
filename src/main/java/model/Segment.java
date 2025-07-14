package model;

public enum Segment {
    BUDGET("Бюджетный"),        // до $100,000
    PREMIUM("Премиум"),         // $100,000–$500,000
    ELITE("Элитный");           // более $500,000

    private final String name;

    Segment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
