package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Base;
import model.Fornecedor;
import model.dao.BaseDAO;
import model.dao.FornecedorDAO;

public class RelatorioPagamentosController {
    @FXML private ComboBox<Base> cbBase;
    @FXML private ComboBox<Fornecedor> cbFornecedor;
    @FXML private ComboBox<String> cbStatus;
    @FXML private DatePicker dtInicio, dtFim;
    @FXML private TableView<?> tabelaResultados;
    @FXML private Label lblTotal;

    @FXML public void initialize() {
        // CARREGA DADOS
        cbBase.setItems(FXCollections.observableArrayList(new BaseDAO().listar()));
        cbFornecedor.setItems(FXCollections.observableArrayList(new FornecedorDAO().listar()));
        cbStatus.setItems(FXCollections.observableArrayList("Todos", "Pago", "Pendente"));
    }

    @FXML public void acaoPesquisar() {
        System.out.println("Pesquisando Pagamentos...");
    }

    @FXML public void acaoLimpar() {
        if(tabelaResultados != null) tabelaResultados.getItems().clear();
        cbBase.getSelectionModel().clearSelection();
        cbFornecedor.getSelectionModel().clearSelection();
    }
}
