package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Base;
import model.Cliente;
import model.dao.BaseDAO;
import model.dao.ClienteDAO;
import model.dao.RelatorioDAO;
import java.util.List;

public class RelatorioRecebimentosController {
    @FXML private ComboBox<Base> cbBase;
    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private ComboBox<String> cbStatus;
    @FXML private DatePicker dtInicio, dtFim;

    @FXML private TableView<String[]> tabelaResultados;
    @FXML private TableColumn<String[], String> colId, colCliente, colVencimento, colStatus, colValor;

    @FXML private Label lblTotal;

    @FXML public void initialize() {
        cbBase.setItems(FXCollections.observableArrayList(new BaseDAO().listar()));
        cbCliente.setItems(FXCollections.observableArrayList(new ClienteDAO().listar()));
        cbStatus.setItems(FXCollections.observableArrayList("Todos", "PENDENTE", "PAGO"));

        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        colCliente.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
        colVencimento.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3]));
        colValor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[4]));
    }

    @FXML public void acaoPesquisar() {
        RelatorioDAO dao = new RelatorioDAO();

        String base = cbBase.getValue() != null ? cbBase.getValue().getRazaoSocial() : null;
        String cliente = cbCliente.getValue() != null ? cbCliente.getValue().getNome() : null;
        String status = cbStatus.getValue();

        List<String[]> dados = dao.buscarRecebimentos(base, cliente, dtInicio.getValue(), dtFim.getValue(), status);

        tabelaResultados.setItems(FXCollections.observableArrayList(dados));

        double total = dados.stream().mapToDouble(s -> Double.parseDouble(s[4])).sum();
        lblTotal.setText(String.format("R$ %.2f", total));
    }

    @FXML public void acaoLimpar() {
        tabelaResultados.getItems().clear();
        cbBase.getSelectionModel().clearSelection();
        cbCliente.getSelectionModel().clearSelection();
        dtInicio.setValue(null);
        dtFim.setValue(null);
        lblTotal.setText("R$ 0,00");
    }
}
