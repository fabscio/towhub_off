package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Base;
import model.Fornecedor;
import model.dao.BaseDAO;
import model.dao.FornecedorDAO;
import model.dao.RelatorioDAO;
import java.util.List;

public class RelatorioPagamentosController {
    @FXML private ComboBox<Base> cbBase;
    @FXML private ComboBox<Fornecedor> cbFornecedor;
    @FXML private ComboBox<String> cbStatus;
    @FXML private DatePicker dtInicio, dtFim;

    // Tabela genérica de Strings (igual ao RelatorioOS)
    @FXML private TableView<String[]> tabelaResultados;
    @FXML private TableColumn<String[], String> colId, colFornecedor, colVencimento, colStatus, colValor;

    @FXML private Label lblTotal;

    @FXML public void initialize() {
        cbBase.setItems(FXCollections.observableArrayList(new BaseDAO().listar()));
        cbFornecedor.setItems(FXCollections.observableArrayList(new FornecedorDAO().listar()));
        cbStatus.setItems(FXCollections.observableArrayList("Todos", "Pago", "Pendente"));

        // Configurar as colunas para ler o array de Strings
        colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        colFornecedor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
        colVencimento.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));
        colStatus.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3]));
        colValor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[4]));
    }

    @FXML public void acaoPesquisar() {
        RelatorioDAO dao = new RelatorioDAO();

        String base = cbBase.getValue() != null ? cbBase.getValue().getRazaoSocial() : null;
        String fornecedor = cbFornecedor.getValue() != null ? cbFornecedor.getValue().getNome() : null;
        String status = cbStatus.getValue();

        List<String[]> dados = dao.buscarPagamentos(base, fornecedor, dtInicio.getValue(), dtFim.getValue(), status);

        tabelaResultados.setItems(FXCollections.observableArrayList(dados));

        // Calcular Total
        double total = dados.stream().mapToDouble(s -> Double.parseDouble(s[4])).sum();
        lblTotal.setText(String.format("R$ %.2f", total));
    }

    @FXML public void acaoLimpar() {
        tabelaResultados.getItems().clear();
        cbBase.getSelectionModel().clearSelection();
        cbFornecedor.getSelectionModel().clearSelection();
        dtInicio.setValue(null);
        dtFim.setValue(null);
        lblTotal.setText("R$ 0,00");
    }
}
