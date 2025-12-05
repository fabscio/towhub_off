package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import model.Base;
import model.Cliente;
import model.Funcionario;
import model.dao.BaseDAO;
import model.dao.ClienteDAO;
import model.dao.FuncionarioDAO;
import model.dao.RelatorioDAO;
import java.time.LocalDate; // Import necessário
import java.util.List;

public class RelatorioOSController {

  @FXML private ComboBox<Base> cbBase;
  @FXML private ComboBox<Cliente> cbCliente;
  @FXML private ComboBox<Funcionario> cbMotorista;
  @FXML private DatePicker dtInicio, dtFim;
  @FXML private TextField txtVeiculo, txtPlaca;
  @FXML private Label lblTotalGeral;

  @FXML private TableView<String[]> tabelaResultados;
  @FXML private TableColumn<String[], String> colId, colData, colCliente, colMotorista, colVeiculo, colValor;

  @FXML
  public void initialize() {
    // CARREGA FILTROS REAIS
    cbBase.setItems(FXCollections.observableArrayList(new BaseDAO().listar()));
    cbCliente.setItems(FXCollections.observableArrayList(new ClienteDAO().listar()));
    cbMotorista.setItems(FXCollections.observableArrayList(new FuncionarioDAO().listarPorFuncao("Motorista")));

    // Configura Colunas (Lê o array de String retornado pelo DAO)
    colId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
    colData.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
    colCliente.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));
    colMotorista.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3]));
    colVeiculo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[4]));
    colValor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[5]));
  }

  @FXML
  public void acaoPesquisar() {
    RelatorioDAO dao = new RelatorioDAO();

    // Obtém os valores dos campos
    String base = (cbBase.getValue() != null) ? cbBase.getValue().getRazaoSocial() : null;
    String cliente = (cbCliente.getValue() != null) ? cbCliente.getValue().getNome() : null;

    // CORREÇÃO: Pegando as datas do formulário
    LocalDate inicio = dtInicio.getValue();
    LocalDate fim = dtFim.getValue();

    // Passa os 4 argumentos para o DAO (Base, Cliente, Inicio, Fim)
    List<String[]> dados = dao.buscarOS(base, cliente, inicio, fim);

    tabelaResultados.setItems(FXCollections.observableArrayList(dados));

    // Calcular total na tela
    double total = 0.0;
    try {
        total = dados.stream().mapToDouble(s -> Double.parseDouble(s[5])).sum();
    } catch (Exception e) { total = 0.0; }

    lblTotalGeral.setText(String.format("R$ %.2f", total));
  }

  @FXML public void acaoLimpar() {
      tabelaResultados.getItems().clear();
      cbBase.getSelectionModel().clearSelection();
      cbCliente.getSelectionModel().clearSelection();
      cbMotorista.getSelectionModel().clearSelection();
      dtInicio.setValue(null); // Limpa data
      dtFim.setValue(null);    // Limpa data
      lblTotalGeral.setText("R$ 0,00");
  }
}
