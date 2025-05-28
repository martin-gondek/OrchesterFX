package com.example.orchesterfx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Udaj {
    private final StringProperty nastroj;
    private final StringProperty dychy;
    private final StringProperty struny;

    public Udaj(String nastroj, String dychy, String struny) {
        this.nastroj = new SimpleStringProperty(nastroj);
        this.dychy = new SimpleStringProperty(dychy);
        this.struny = new SimpleStringProperty(struny);


    }

    public StringProperty nastrojProperty() {
        return nastroj;
    }

    public StringProperty strunyProperty() {
        return struny;
    }

    public StringProperty dychyProperty() {
        return dychy;
    }


}
