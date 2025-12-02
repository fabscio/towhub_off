package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreateOrderController {

    // --- Metade de Cima ---
    @FXML private TextField txtId;
    @FXML private ComboBox<String> cbMotorista;
    @FXML private ComboBox<String> cbAnalista;
    @FXML private ComboBox<String> cbPagamento;
    @FXML private TextField txtVeiculo;
    @FXML private TextField txtPlaca;
    @FXML private TextField txtContato;

    @FXML private ComboBox<String> cbSolicitacao;
    @FXML private DatePicker dtData;
    @FXML private ComboBox<String> cbBase;
    @FXML private ComboBox<String> cbCliente;
    @FXML private TextField txtOrigem;
    @FXML private TextField txtDestino;
    @FXML private TextField txtPrazo;

    // --- Metade de Baixo ---
    @FXML private TableView<ItemServico> tabelaServicos;
    @FXML private TableColumn<ItemServico, String> colServico;
    @FXML private TableColumn<ItemServico, Integer> colQtd;
    @FXML private TableColumn<ItemServico, Double> colValor;
    @FXML private TableColumn<ItemServico, Double> colSubtotal;
    
    @FXML private Label lblValorTotal;

    // Lista para controlar os dados da tabela
    private ObservableList<ItemServico> listaServicos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        carregarListas();
        configurarTabela();
    }

    private void carregarListas() {
        // Dados estáticos para teste (depois virão do Banco de Dados)
        cbPagamento.setItems(FXCollections.observableArrayList("Dinheiro", "Pix", "Lote"));
        cbSolicitacao.setItems(FXCollections.observableArrayList("Imediato", "Agendamento"));
        
        // Simulação de dados
        cbMotorista.setItems(FXCollections.observableArrayList("João Silva", "Maria Oliveira"));
        cbAnalista.setItems(FXCollections.observableArrayList("Carlos TI", "Ana RH"));
        cbBase.setItems(FXCollections.observableArrayList("Base Centro", "Base Norte"));
        cbCliente.setItems(FXCollections.observableArrayList("Seguradora X", "Particular"));
    }

    private void configurarTabela() {
        colServico.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        tabelaServicos.setItems(listaServicos);
    }

    // --- Ações dos Botões ---

    @FXML
    public void handleAdicionarServico() {
        // TODO: Aqui abrirá o popup futuramente. 
        // Por enquanto, adiciono um item de teste:
        ItemServico item = new ItemServico("Guincho Leve", 1, 150.00);
        listaServicos.add(item);
        atualizarTotal();
    }

    @FXML
    public void handleRemoverServico() {
        ItemServico selecionado = tabelaServicos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            listaServicos.remove(selecionado);
            atualizarTotal();
        } else {
            System.out.println("Selecione um item para remover");
        }
    }

    @FXML
    public void handleConcluir() {
        System.out.println("Salvando ordem de serviço...");
        // Lógica de salvar no banco
    }

    private void atualizarTotal() {
        double total = 0;
        for (ItemServico item : listaServicos) {
            total += item.getSubtotal();
        }
        lblValorTotal.setText(String.format("R$ %.2f", total));
    }

    // --- Classe Modelo para a Tabela ---
    public static class ItemServico {
        private String descricao;
        private int quantidade;
        private double valorUnitario;
        
        public ItemServico(String descricao, int quantidade, double valorUnitario) {
            this.descricao = descricao;
            this.quantidade = quantidade;
            this.valorUnitario = valorUnitario;
        }

        public String getDescricao() { return descricao; }
        public int getQuantidade() { return quantidade; }
        public double getValorUnitario() { return valorUnitario; }
        public double getSubtotal() { return quantidade * valorUnitario; }
    }
}
