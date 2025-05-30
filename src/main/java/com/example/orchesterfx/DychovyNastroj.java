package com.example.orchesterfx;

public class DychovyNastroj extends HudobnyNastroj {
    private int pocetDier;

    public DychovyNastroj(String nazov, double cena, String zvuk, String hrac, int pocetDier) {
        super(nazov, cena, zvuk, hrac);
        this.pocetDier = pocetDier;
    }

    @Override
    public String getTyp() {
        return "Dychový";
    }

    @Override
    public String getDetail() {
        return "Pocet dier: " + pocetDier;
    }
}
