package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class ReportPaymentsController {
    @FXML private ComboBox<String> cbBase, cbFornecedor, cbStatus;
    @FXML private DatePicker dtInicio, dtFim;
    @FXML private TableView<PagModel> tabelaResultados;
    @FXML private Label lblTotal;
    
    @FXML private TableColumn<PagModel, String> colId, colFornecedor, colStatus;
    @FXML private TableColumn<PagModel, LocalDate> colVencimento;
    @FXML private TableColumn<PagModel, Double> colValor;

    @FXML public void initialize() {
        cbStatus.setItems(FXCollections.observableArrayList("Todos", "Pago", "Pendente"));
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFornecedor.setCellValueFactory(new PropertyValueFactory<>("fornecedor"));
        colVencimento.setCellValueFactory(new PropertyValueFactory<>("vencimento"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

    @FXML public void handlePesquisar() {
        tabelaResultados.setItems(FXCollections.observableArrayList(
            new PagModel("PAG_05", "Posto Shell", LocalDate.now(), "Pago", 250.00)
        ));
        lblTotal.setText("R$ 250,00");
    }

    @FXML public void handleLimpar() { tabelaResultados.getItems().clear(); lblTotal.setText("R$ 0,00"); }

    public static class PagModel {
        private String id, fornecedor, status; LocalDate vencimento; Double valor;
        public PagModel(String id, String fornecedor, LocalDate vencimento, String status, Double valor) {
            this.id=id; this.fornecedor=fornecedor; this.vencimento=vencimento; this.status=status; this.valor=valor;
        }
        public String getId() { return id; }
        public String getFornecedor() { return fornecedor; }
        public LocalDate getVencimento() { return vencimento; }
        public String getStatus() { return status; }
        public Double getValor() { return valor; }
    }
}
