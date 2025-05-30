package com.example.orchesterfx;

public class StrunovyNastroj extends HudobnyNastroj {
    private int pocetStrun;

    public StrunovyNastroj(String nazov, double cena, int pocetStrun) {
        super(nazov, cena);
        this.pocetStrun = pocetStrun;
    }

    public int getPocetStrun() {
        return pocetStrun;
    }

    @Override
    public String getTyp() {
        return "Strunový";
    }

    @Override
    public String getDetail() {
        return "Struny: " + pocetStrun;
    }
}
