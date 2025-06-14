package com.example.orchesterfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.converter.DoubleStringConverter;

import java.io.*;

public class HelloController {
    @FXML private Button vypocitajButton;
    @FXML private Label vysledokLabel;

    @FXML private ComboBox<String> typCombo;
    @FXML private TextField nazovField, cenaField, zvukField, hracField, specifickyField;
    @FXML private Label specifickyLabel;

    @FXML private TableView<HudobnyNastroj> tableView;
    @FXML private TableColumn<HudobnyNastroj, String> colTyp, colNazov, colCena, colZvuk, colHrac, colDetail;

    @FXML private Button pridajButton, vymazButton;

    private final ObservableList<HudobnyNastroj> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        typCombo.setItems(FXCollections.observableArrayList("Dychový", "Strunový", "Klávesový"));
        typCombo.setOnAction(e -> aktualizujPopisSpecifikacie());

        tableView.setEditable(true);

        colTyp.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTyp()));
        colNazov.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNazov()));
        colNazov.setCellFactory(TextFieldTableCell.forTableColumn());
        colNazov.setOnEditCommit(e -> {
            e.getRowValue().setNazov(e.getNewValue());
            aktualizujSubor();
        });

        colCena.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCena())));
        colCena.setCellFactory(TextFieldTableCell.forTableColumn());
        colCena.setOnEditCommit(e -> {
            try {
                double novaCena = Double.parseDouble(e.getNewValue());
                e.getRowValue().setCena(novaCena);
                aktualizujSubor();
            } catch (NumberFormatException ex) {
                System.out.println("Chybná cena");
            }
        });

        colZvuk.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getZvuk()));
        colZvuk.setCellFactory(TextFieldTableCell.forTableColumn());
        colZvuk.setOnEditCommit(e -> {
            e.getRowValue().setZvuk(e.getNewValue());
            aktualizujSubor();
        });

        colHrac.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getHrac()));
        colHrac.setCellFactory(TextFieldTableCell.forTableColumn());
        colHrac.setOnEditCommit(e -> {
            e.getRowValue().setHrac(e.getNewValue());
            aktualizujSubor();
        });

        colDetail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDetail()));

        tableView.setItems(data);

        pridajButton.setOnAction(e -> pridajNastroj());
        vymazButton.setOnAction(e -> vymazNastroj());
        vypocitajButton.setOnAction(e -> vypocitajPocet());

        nacitajZoSuboru();
    }

    private void vypocitajPocet() {
        String typ = typCombo.getValue();
        int sucet = 0;

        for (HudobnyNastroj n : data) {
            if ("Dychový".equals(typ) && n instanceof DychovyNastroj dych) {
                sucet += Integer.parseInt(dych.getPocetDier());
            } else if ("Strunový".equals(typ) && n instanceof StrunovyNastroj strun) {
                sucet += Integer.parseInt(strun.getPocetStrun());
            } else if ("Klávesový".equals(typ) && n instanceof KlavesovyNastroj klav) {
                sucet += Integer.parseInt(klav.getPocetKlaves());
            }
        }

        vysledokLabel.setText("Celkový počet pre " + typ + ": " + sucet);
    }

    private void aktualizujPopisSpecifikacie() {
        String typ = typCombo.getValue();
        if ("Dychový".equals(typ)) {
            specifickyLabel.setText("Počet dier:");
        } else if ("Strunový".equals(typ)) {
            specifickyLabel.setText("Počet strún:");
        } else if ("Klávesový".equals(typ)) {
            specifickyLabel.setText("Počet kláves:");
        }
    }

    private void pridajNastroj() {
        try {
            String typ = typCombo.getValue();
            String nazov = nazovField.getText();
            double cena = Double.parseDouble(cenaField.getText());
            String zvuk = zvukField.getText();
            String hrac = hracField.getText();
            int specifickaHodnota = Integer.parseInt(specifickyField.getText());

            HudobnyNastroj nastroj = switch (typ) {
                case "Dychový" -> new DychovyNastroj(nazov, cena, zvuk, hrac, specifickaHodnota);
                case "Strunový" -> new StrunovyNastroj(nazov, cena, zvuk, hrac, specifickaHodnota);
                case "Klávesový" -> new KlavesovyNastroj(nazov, cena, zvuk, hrac, specifickaHodnota);
                default -> null;
            };

            if (nastroj != null) {
                data.add(nastroj);
                zapisDoSuboru(nastroj);
                vymazPolia();
            }
        } catch (NumberFormatException e) {
            System.out.println("Zlá hodnota – cena a počet musia byť čísla.");
        }
    }

    private void vymazNastroj() {
        HudobnyNastroj vybrany = tableView.getSelectionModel().getSelectedItem();
        if (vybrany != null) {
            data.remove(vybrany);
            aktualizujSubor();
        }
    }

    private void zapisDoSuboru(HudobnyNastroj n) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("sklad.txt", true))) {
            String riadok = String.join(",",
                    n.getTyp(),
                    n.getNazov(),
                    String.valueOf(n.getCena()),
                    n.getZvuk(),
                    n.getHrac(),
                    n.getDetail());
            bw.write(riadok);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Chyba pri zápise.");
        }
    }

    private void aktualizujSubor() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("sklad.txt"))) {
            for (HudobnyNastroj n : data) {
                String riadok = String.join(",",
                        n.getTyp(),
                        n.getNazov(),
                        String.valueOf(n.getCena()),
                        n.getZvuk(),
                        n.getHrac(),
                        n.getDetail());
                bw.write(riadok);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Chyba pri zápise.");
        }
    }

    private void nacitajZoSuboru() {
        try (BufferedReader br = new BufferedReader(new FileReader("sklad.txt"))) {
            String riadok;
            while ((riadok = br.readLine()) != null) {
                String[] pole = riadok.split(",", 6);
                if (pole.length == 6) {
                    String typ = pole[0];
                    String nazov = pole[1];
                    double cena = Double.parseDouble(pole[2]);
                    String zvuk = pole[3];
                    String hrac = pole[4];
                    int specificky = Integer.parseInt(pole[5].replaceAll("\\D+", ""));

                    HudobnyNastroj n = switch (typ) {
                        case "Dychový" -> new DychovyNastroj(nazov, cena, zvuk, hrac, specificky);
                        case "Strunový" -> new StrunovyNastroj(nazov, cena, zvuk, hrac, specificky);
                        case "Klávesový" -> new KlavesovyNastroj(nazov, cena, zvuk, hrac, specificky);
                        default -> null;
                    };

                    if (n != null) data.add(n);
                }
            }
        } catch (IOException e) {
            System.out.println("Chyba pri čítaní súboru.");
        }
    }

    private void vymazPolia() {
        nazovField.clear();
        cenaField.clear();
        zvukField.clear();
        hracField.clear();
        specifickyField.clear();
    }
}
