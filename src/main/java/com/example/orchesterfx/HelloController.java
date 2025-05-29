package com.example.orchesterfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Button vymazButton;


    @FXML
    private TextField nastroj;

    @FXML
    private TextField dychy;

    @FXML
    private TextField struny;

    @FXML
    private TableView<Udaj> tableView;

    @FXML
    private TableColumn<Udaj, String> col1;

    @FXML
    private TableColumn<Udaj,String > col3;

    @FXML
    private TableColumn<Udaj, String> col2;

    private final ObservableList<Udaj> data = FXCollections.observableArrayList();

    @FXML
    private Button zapisButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col1.setOnEditCommit(event -> {
            Udaj udaj = event.getRowValue();
            udaj.nastrojProperty().set(event.getNewValue());
            aktualizujSubor();
        });

        col2.setOnEditCommit(event -> {
            Udaj udaj = event.getRowValue();
            udaj.dychyProperty().set(event.getNewValue());
            aktualizujSubor();
        });

        col3.setOnEditCommit(event -> {
            Udaj udaj = event.getRowValue();
            udaj.strunyProperty().set(event.getNewValue());
            aktualizujSubor();
        });

        tableView.setEditable(true);
        col1.setCellFactory(TextFieldTableCell.forTableColumn());
        col2.setCellFactory(TextFieldTableCell.forTableColumn());
        col3.setCellFactory(TextFieldTableCell.forTableColumn());
        vymazButton.setOnAction(event -> vymazUdaj());
        col1.setCellValueFactory(cellData -> cellData.getValue().nastrojProperty());
        col2.setCellValueFactory(cellData -> cellData.getValue().dychyProperty());
        col3.setCellValueFactory(cellData -> cellData.getValue().strunyProperty());


        tableView.setItems(data);

        try (BufferedReader br = new BufferedReader(new FileReader("sklad.txt"))) {
            String riadok;
            while ((riadok = br.readLine()) != null) {
                String[] slova = riadok.split(",");
                if (slova.length == 3) {
                    data.add(new Udaj(slova[0], slova[1], slova[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Chyba pri čítaní súboru.");
        }

        zapisButton.setOnAction(event -> zapisUdaj());
    }

    private void zapisUdaj() {
        String n = nastroj.getText();
        String d = dychy.getText();
        String s = struny.getText();

        if (n.isEmpty() || d.isEmpty() || s.isEmpty()) {
            System.out.println("Vyplň všetky polia.");
            return;
        }

        String zapis = n + "," + d + "," + s;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("sklad.txt", true))) {
            bw.write(zapis);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Nepodarilo sa zapísať do súboru.");
        }

        data.add(new Udaj(n, d, s));
        nastroj.clear();
        dychy.clear();
        struny.clear();
    }


    private void vymazUdaj() {
        Udaj vybrany = tableView.getSelectionModel().getSelectedItem();

        if (vybrany == null) {
            System.out.println("najprv vyber riadok.");
            return;
        }

        data.remove(vybrany);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("sklad.txt"))) {
            for (Udaj u : data) {
                String riadok = u.nastrojProperty().get() + "," + u.dychyProperty().get() + "," + u.strunyProperty().get();
                bw.write(riadok);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("chyba pri aktualizacii.");
        }
    }

    private void aktualizujSubor() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("sklad.txt"))) {
            for (Udaj u : data) {
                String riadok = u.nastrojProperty().get() + "," + u.dychyProperty().get() + "," + u.strunyProperty().get();
                bw.write(riadok);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("chyba pri zapise do suboru.");
        }
    }


}
