package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class ReportReceiptsController {
    @FXML private ComboBox<String> cbBase, cbCliente, cbStatus;
    @FXML private DatePicker dtInicio, dtFim;
    @FXML private TableView<RecModel> tabelaResultados;
    @FXML private Label lblTotal;
    
    @FXML private TableColumn<RecModel, String> colId, colCliente, colStatus;
    @FXML private TableColumn<RecModel, LocalDate> colVencimento;
    @FXML private TableColumn<RecModel, Double> colValor;

    @FXML public void initialize() {
        cbStatus.setItems(FXCollections.observableArrayList("Todos", "Pago", "Pendente"));
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colVencimento.setCellValueFactory(new PropertyValueFactory<>("vencimento"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

    @FXML public void handlePesquisar() {
        tabelaResultados.setItems(FXCollections.observableArrayList(
            new RecModel("LOTE_01", "Seguradora Y", LocalDate.now(), "Pendente", 5000.00)
        ));
        lblTotal.setText("R$ 5000,00");
    }

    @FXML public void handleLimpar() { tabelaResultados.getItems().clear(); lblTotal.setText("R$ 0,00"); }

    public static class RecModel {
        private String id, cliente, status; LocalDate vencimento; Double valor;
        public RecModel(String id, String cliente, LocalDate vencimento, String status, Double valor) {
            this.id=id; this.cliente=cliente; this.vencimento=vencimento; this.status=status; this.valor=valor;
        }
        public String getId() { return id; }
        public String getCliente() { return cliente; }
        public LocalDate getVencimento() { return vencimento; }
        public String getStatus() { return status; }
        public Double getValor() { return valor; }
    }
}
