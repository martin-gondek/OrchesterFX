package com.example.orchesterfx;

public class KlavesovyNastroj extends HudobnyNastroj {
    private int pocetKlaves;

    public KlavesovyNastroj(String nazov, double cena, String zvuk, String hrac, int pocetKlaves) {
        super(nazov, cena, zvuk, hrac);
        this.pocetKlaves = pocetKlaves;
    }
    @Override
    public String getTyp() {
        return "Klávesový";
    }

    @Override
    public String getDetail() {
        return "Pocet klaves: " + pocetKlaves;
    }
}
