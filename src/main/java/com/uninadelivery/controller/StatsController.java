package com.uninadelivery.controller;

import com.uninadelivery.model.dao.OrdineDAO;
import com.uninadelivery.model.entities.Ordine;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StatsController {
    @FXML private ComboBox<String> monthComboBox;
    @FXML private Button generateReportButton;
    @FXML private Label avgOrdersLabel;
    @FXML private Label maxOrderLabel;
    @FXML private Label minOrderLabel;
    @FXML private BarChart<String, Number> barChart;

    private final OrdineDAO ordineDAO = new OrdineDAO();

    @FXML
    public void initialize() {
        // Popola il ComboBox con i mesi
        monthComboBox.setItems(FXCollections.observableArrayList(
                "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno",
                "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"
        ));

        generateReportButton.setOnAction(event -> generateReport());
    }

    private void generateReport() {
        String selectedMonth = monthComboBox.getValue();
        if (selectedMonth == null) return;

        int monthNumber = monthComboBox.getItems().indexOf(selectedMonth) + 1;
        List<Ordine> ordini = ordineDAO.getAllOrdini().stream()
                .filter(o -> o.getDataOrdine().getMonthValue() == monthNumber)
                .collect(Collectors.toList());

        if (ordini.isEmpty()) {
            avgOrdersLabel.setText("Nessun dato");
            maxOrderLabel.setText("Nessun dato");
            minOrderLabel.setText("Nessun dato");
            barChart.getData().clear();
            return;
        }

        // Numero medio di ordini
        double avgOrders = (double) ordini.size() / Month.of(monthNumber).length(false);
        avgOrdersLabel.setText(String.format("%.2f", avgOrders));

        // Ordine con più prodotti
        Ordine maxOrder = ordini.stream().max(Comparator.comparingInt(Ordine::getNumeroProdotti)).orElse(null);
        maxOrderLabel.setText(maxOrder != null ? "ID: " + maxOrder.getIdOrdine() + " - Prodotti: " + maxOrder.getNumeroProdotti() : "Nessun dato");

        // Ordine con meno prodotti
        Ordine minOrder = ordini.stream().min(Comparator.comparingInt(Ordine::getNumeroProdotti)).orElse(null);
        minOrderLabel.setText(minOrder != null ? "ID: " + minOrder.getIdOrdine() + " - Prodotti: " + minOrder.getNumeroProdotti() : "Nessun dato");

        // Popolare il grafico a barre
        updateBarChart(ordini);
    }

    private void updateBarChart(List<Ordine> ordini) {
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Ordini per giorno");

        ordini.stream().collect(Collectors.groupingBy(o -> o.getDataOrdine().getDayOfMonth(), Collectors.counting()))
                .forEach((day, count) -> series.getData().add(new XYChart.Data<>(String.valueOf(day), count)));

        barChart.getData().add(series);
    }
}