package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RelatorioPagamentosController {
    @FXML private ComboBox<String> cbBase, cbFornecedor, cbStatus;
    @FXML private DatePicker dtInicio, dtFim;
    @FXML private TableView<?> tabelaResultados;
    @FXML private Label lblTotal;

    @FXML private TableColumn<?, ?> colId, colFornecedor, colVencimento, colStatus, colValor;

    @FXML public void initialize() {
        cbStatus.setItems(FXCollections.observableArrayList("Todos", "Pago", "Pendente"));
    }

    @FXML public void acaoPesquisar() {
        System.out.println("Pesquisando Pagamentos...");
        lblTotal.setText("R$ 0,00");
    }

    @FXML public void acaoLimpar() {
        if(tabelaResultados != null) tabelaResultados.getItems().clear();
        lblTotal.setText("R$ 0,00");
    }
}
