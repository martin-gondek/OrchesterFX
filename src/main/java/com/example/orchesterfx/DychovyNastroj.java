package com.example.orchesterfx;

public class DychovyNastroj extends HudobnyNastroj {
    private int pocetDier;

    public DychovyNastroj(String nazov, double cena, int pocetDier) {
        super(nazov, cena);
        this.pocetDier = pocetDier;
    }

    public int getPocetDier() {
        return pocetDier;
    }

    @Override
    public String getTyp() {
        return "Dychový";
    }

    @Override
    public String getDetail() {
        return "Dier: " + pocetDier;
    }
}
