package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import model.dao.RelatorioDAO;
import java.util.List;

public class RelatorioOSController {
  @FXML private TableView<String[]> tabelaResultados;
  @FXML private TableColumn<String[], String> colId, colData, colCliente, colValor;

  @FXML
  public void initialize() {
    // Configura colunas para ler vetor de String
    colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
    colData.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
    colCliente.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));
    colValor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[5]));
  }

  @FXML
  public void acaoPesquisar() {
    RelatorioDAO dao = new RelatorioDAO();
    List<String[]> dados = dao.buscarOS(null, null);
    tabelaResultados.setItems(FXCollections.observableArrayList(dados));
  }

  @FXML public void acaoLimpar() { tabelaResultados.getItems().clear(); }
}
