package com.example.orchesterfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;

public class HelloController {

    @FXML private ComboBox<String> typCombo;
    @FXML private TextField nazovField;
    @FXML private TextField cenaField;
    @FXML private TextField specifickyField;
    @FXML private Label specifickyLabel;

    @FXML private TableView<HudobnyNastroj> tableView;
    @FXML private TableColumn<HudobnyNastroj, String> colTyp;
    @FXML private TableColumn<HudobnyNastroj, String> colNazov;
    @FXML private TableColumn<HudobnyNastroj, String> colCena;
    @FXML private TableColumn<HudobnyNastroj, String> colDetail;

    @FXML private Button pridajButton;
    @FXML private Button vymazButton;

    private final ObservableList<HudobnyNastroj> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        typCombo.setItems(FXCollections.observableArrayList("Dychový", "Strunový"));
        typCombo.setOnAction(e -> aktualizujPopisSpecifikacie());

        colTyp.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTyp()));
        colNazov.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNazov()));
        colCena.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCena())));
        colDetail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDetail()));

        tableView.setItems(data);

        pridajButton.setOnAction(e -> pridajNastroj());
        vymazButton.setOnAction(e -> vymazNastroj());

        nacitajZoSuboru();
    }

    private void aktualizujPopisSpecifikacie() {
        String typ = typCombo.getValue();
        if ("Dychový".equals(typ)) {
            specifickyLabel.setText("Počet dier:");
        } else if ("Strunový".equals(typ)) {
            specifickyLabel.setText("Počet strún:");
        }
    }

    private void pridajNastroj() {
        try {
            String typ = typCombo.getValue();
            String nazov = nazovField.getText();
            double cena = Double.parseDouble(cenaField.getText());
            int specifickaHodnota = Integer.parseInt(specifickyField.getText());

            HudobnyNastroj nastroj = switch (typ) {
                case "Dychový" -> new DychovyNastroj(nazov, cena, specifickaHodnota);
                case "Strunový" -> new StrunovyNastroj(nazov, cena, specifickaHodnota);
                default -> null;
            };

            if (nastroj != null) {
                data.add(nastroj);
                zapisDoSuboru(nastroj);
                vymazPolia();
            }
        } catch (NumberFormatException e) {
            System.out.println("Chybný vstup – cena a počet musia byť čísla.");
        }
    }

    private void vymazNastroj() {
        HudobnyNastroj vybrany = tableView.getSelectionModel().getSelectedItem();
        if (vybrany != null) {
            data.remove(vybrany);
            aktualizujSubor();
        }
    }

    private void zapisDoSuboru(HudobnyNastroj nastroj) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("sklad.txt", true))) {
            bw.write(nastroj.getTyp() + "," + nastroj.getNazov() + "," + nastroj.getCena() + "," + nastroj.getDetail());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Chyba pri zápise.");
        }
    }

    private void aktualizujSubor() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("sklad.txt"))) {
            for (HudobnyNastroj n : data) {
                bw.write(n.getTyp() + "," + n.getNazov() + "," + n.getCena() + "," + n.getDetail());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Chyba pri prepise súboru.");
        }
    }

    private void nacitajZoSuboru() {
        try (BufferedReader br = new BufferedReader(new FileReader("sklad.txt"))) {
            String riadok;
            while ((riadok = br.readLine()) != null) {
                String[] pole = riadok.split(",");
                if (pole.length == 4) {
                    String typ = pole[0];
                    String nazov = pole[1];
                    double cena = Double.parseDouble(pole[2]);
                    int specificka = Integer.parseInt(pole[3].replaceAll("[^\\d]", ""));

                    HudobnyNastroj nastroj = switch (typ) {
                        case "Dychový" -> new DychovyNastroj(nazov, cena, specificka);
                        case "Strunový" -> new StrunovyNastroj(nazov, cena, specificka);
                        default -> null;
                    };

                    if (nastroj != null) {
                        data.add(nastroj);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Chyba pri čítaní súboru.");
        }
    }

    private void vymazPolia() {
        nazovField.clear();
        cenaField.clear();
        specifickyField.clear();
    }
}
