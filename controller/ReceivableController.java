package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class ReceivableController {

    // --- Header ---
    @FXML private ComboBox<String> cbCliente;
    @FXML private DatePicker dtEmissao;
    @FXML private DatePicker dtVencimento;
    @FXML private ComboBox<String> cbStatus;

    // --- Tabela e Controles ---
    @FXML private TableView<OsItem> tabelaOs;
    @FXML private TableColumn<OsItem, String> colIdOs;
    @FXML private TableColumn<OsItem, LocalDate> colDataOs;
    @FXML private TableColumn<OsItem, Double> colValorOs;

    @FXML private ComboBox<OsItem> cbOsDisponiveis; // ComboBox agora guarda objetos OsItem
    @FXML private Label lblTotalLote;

    private ObservableList<OsItem> listaOsAdicionadas = FXCollections.observableArrayList();
    private ObservableList<OsItem> listaOsDoBanco = FXCollections.observableArrayList(); // Simula o BD

    @FXML
    public void initialize() {
        carregarDadosIniciais();
        configurarTabela();
    }

    private void carregarDadosIniciais() {
        cbCliente.setItems(FXCollections.observableArrayList("Seguradora Alpha", "Transportadora Beta"));
        cbStatus.setItems(FXCollections.observableArrayList("Pendente", "Pago"));
        cbStatus.getSelectionModel().selectFirst();

        // SIMULAÇÃO: Carregar OSs do banco que pertencem ao cliente e não foram pagas
        // Na prática, você fará: dao.buscarOsPendentes(idCliente);
        listaOsDoBanco.add(new OsItem("OS_1001", LocalDate.now().minusDays(5), 150.00));
        listaOsDoBanco.add(new OsItem("OS_1002", LocalDate.now().minusDays(3), 300.50));
        listaOsDoBanco.add(new OsItem("OS_1005", LocalDate.now().minusDays(1), 1200.00));

        cbOsDisponiveis.setItems(listaOsDoBanco);
    }

    private void configurarTabela() {
        colIdOs.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDataOs.setCellValueFactory(new PropertyValueFactory<>("data"));
        colValorOs.setCellValueFactory(new PropertyValueFactory<>("valor"));

        tabelaOs.setItems(listaOsAdicionadas);
    }

    @FXML
    public void handleAdicionarOs() {
        // Pega o item selecionado no ComboBox
        OsItem osSelecionada = cbOsDisponiveis.getSelectionModel().getSelectedItem();

        if (osSelecionada != null) {
            // Verifica se já não foi adicionada
            if (!listaOsAdicionadas.contains(osSelecionada)) {
                listaOsAdicionadas.add(osSelecionada);
                atualizarTotal();

                // Opcional: Remover do combo para não adicionar duas vezes
                // cbOsDisponiveis.getItems().remove(osSelecionada);
            } else {
                System.out.println("Esta OS já foi adicionada ao lote.");
            }
        }
    }

    @FXML
    public void handleRemoverOs() {
        OsItem selecionada = tabelaOs.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            listaOsAdicionadas.remove(selecionada);
            atualizarTotal();

            // Opcional: Devolver para o combo
            // cbOsDisponiveis.getItems().add(selecionada);
        }
    }

    private void atualizarTotal() {
        double total = 0;
        for (OsItem os : listaOsAdicionadas) {
            total += os.getValor();
        }
        lblTotalLote.setText(String.format("R$ %.2f", total));
    }

    @FXML
    public void handleSalvar() {
        System.out.println("Gerando Lote com " + listaOsAdicionadas.size() + " OSs.");
        // Lógica de salvar no BD: Vincular ID_LOTE às OSs selecionadas
    }

    // --- Classe Modelo para a Tabela e ComboBox ---
    public static class OsItem {
        private String id;
        private LocalDate data;
        private Double valor;

        public OsItem(String id, LocalDate data, Double valor) {
            this.id = id;
            this.data = data;
            this.valor = valor;
        }

        public String getId() { return id; }
        public LocalDate getData() { return data; }
        public Double getValor() { return valor; }

        // Importante para o ComboBox mostrar um texto bonito e não o endereço de memória
        @Override
        public String toString() {
            return id + " - R$ " + valor;
        }
    }
}
