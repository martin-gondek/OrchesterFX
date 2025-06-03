package com.example.orchesterfx;

public class StrunovyNastroj extends HudobnyNastroj {
    private int pocetStrun;

    public StrunovyNastroj(String nazov, double cena, String zvuk, String hrac, int pocetStrun) {
        super(nazov, cena, zvuk, hrac);
        this.pocetStrun = pocetStrun;
    }

    public String getPocetStrun() {
        return String.valueOf(pocetStrun);
    }

    @Override
    public String getTyp() {
        return "Strunový";
    }

    @Override
    public String getDetail() {
        return "Pocet strun: " + pocetStrun;
    }
}
