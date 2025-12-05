package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Base;
import model.Cliente;
import model.dao.BaseDAO;
import model.dao.ClienteDAO;

public class RelatorioRecebimentosController {
    @FXML private ComboBox<Base> cbBase;
    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private ComboBox<String> cbStatus;
    @FXML private DatePicker dtInicio, dtFim;
    @FXML private TableView<?> tabelaResultados;
    @FXML private Label lblTotal;

    @FXML public void initialize() {
        // CARREGA DADOS
        cbBase.setItems(FXCollections.observableArrayList(new BaseDAO().listar()));
        cbCliente.setItems(FXCollections.observableArrayList(new ClienteDAO().listar()));
        cbStatus.setItems(FXCollections.observableArrayList("Todos", "Pago", "Pendente"));
    }

    @FXML public void acaoPesquisar() {
        // Implementar lógica de busca no DAO depois
        System.out.println("Pesquisando Recebimentos...");
    }

    @FXML public void acaoLimpar() {
        if(tabelaResultados != null) tabelaResultados.getItems().clear();
        cbBase.getSelectionModel().clearSelection();
        cbCliente.getSelectionModel().clearSelection();
    }
}
