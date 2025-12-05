package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dao.RelatorioDAO; // Assumindo que você criou o DAO genérico ou específico
import java.time.LocalDate;

public class RelatorioRecebimentosController {
    @FXML private ComboBox<String> cbBase, cbCliente, cbStatus;
    @FXML private DatePicker dtInicio, dtFim;
    @FXML private TableView<String> tabelaResultados; // Simplificado para compilar
    @FXML private Label lblTotal;

    // Colunas (ajuste o tipo conforme seu modelo de dados do relatório)
    @FXML private TableColumn<?, ?> colId, colCliente, colVencimento, colStatus, colValor;

    @FXML public void initialize() {
        cbStatus.setItems(FXCollections.observableArrayList("Todos", "Pago", "Pendente"));
    }

    @FXML public void acaoPesquisar() {
        System.out.println("Pesquisando Recebimentos...");
        lblTotal.setText("R$ 0,00");
    }

    @FXML public void acaoLimpar() {
        if(tabelaResultados != null) tabelaResultados.getItems().clear();
        lblTotal.setText("R$ 0,00");
    }
}
