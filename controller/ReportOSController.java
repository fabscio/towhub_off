package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class ReportOSController {

    @FXML private ComboBox<String> cbBase, cbCliente, cbMotorista;
    @FXML private DatePicker dtInicio, dtFim;
    @FXML private TextField txtVeiculo, txtPlaca;
    @FXML private TableView<DadosOS> tabelaResultados;
    @FXML private Label lblTotalGeral;

    // Colunas
    @FXML private TableColumn<DadosOS, String> colId, colCliente, colMotorista, colVeiculo;
    @FXML private TableColumn<DadosOS, LocalDate> colData;
    @FXML private TableColumn<DadosOS, Double> colValor;

    @FXML
    public void initialize() {
        // Preencher combos com "Todos" ou dados do banco
        cbBase.setItems(FXCollections.observableArrayList("Todas", "Base Matriz", "Base Filial"));
        
        // Configurar Colunas
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        colMotorista.setCellValueFactory(new PropertyValueFactory<>("motorista"));
        colVeiculo.setCellValueFactory(new PropertyValueFactory<>("veiculo"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

    @FXML
    public void handlePesquisar() {
        System.out.println("Pesquisando OS com filtros...");
        // Aqui vai o SELECT * FROM OS WHERE ...
        
        // Dados Dummy
        ObservableList<DadosOS> dados = FXCollections.observableArrayList(
            new DadosOS("1001", LocalDate.now(), "Seguradora X", "João", "Fiat Uno", 150.00),
            new DadosOS("1002", LocalDate.now().minusDays(1), "Particular", "Maria", "Gol", 200.00)
        );
        tabelaResultados.setItems(dados);
        
        // Atualizar Label Total
        double total = dados.stream().mapToDouble(DadosOS::getValor).sum();
        lblTotalGeral.setText(String.format("R$ %.2f", total));
    }

    @FXML
    public void handleLimpar() {
        cbBase.setValue(null); dtInicio.setValue(null); dtFim.setValue(null);
        cbCliente.setValue(null); cbMotorista.setValue(null);
        txtVeiculo.clear(); txtPlaca.clear();
        tabelaResultados.getItems().clear();
        lblTotalGeral.setText("R$ 0,00");
    }

    // Classe Modelo Interna
    public static class DadosOS {
        private String id, cliente, motorista, veiculo;
        private LocalDate data;
        private Double valor;

        public DadosOS(String id, LocalDate data, String cliente, String motorista, String veiculo, Double valor) {
            this.id = id; this.data = data; this.cliente = cliente; 
            this.motorista = motorista; this.veiculo = veiculo; this.valor = valor;
        }
        // Getters necessários para o PropertyValueFactory
        public String getId() { return id; }
        public LocalDate getData() { return data; }
        public String getCliente() { return cliente; }
        public String getMotorista() { return motorista; }
        public String getVeiculo() { return veiculo; }
        public Double getValor() { return valor; }
    }
}
