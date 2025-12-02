package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class PayableController {

    @FXML private DatePicker dtCompra;
    @FXML private ComboBox<String> cbBase;
    @FXML private ComboBox<String> cbFornecedor;

    @FXML private TableView<Parcela> tabelaParcelas;
    @FXML private TableColumn<Parcela, String> colIdParcela;
    @FXML private TableColumn<Parcela, LocalDate> colVencimento;
    @FXML private TableColumn<Parcela, Double> colValor;

    @FXML private TextField txtValorParcela;
    @FXML private DatePicker dtVencimentoParcela;
    @FXML private Label lblTotal;

    private ObservableList<Parcela> listaParcelas = FXCollections.observableArrayList();
    private int contadorParcelas = 1;

    @FXML
    public void initialize() {
        // Dados de teste
        cbBase.setItems(FXCollections.observableArrayList("Base Matriz", "Base Filial 1"));
        cbFornecedor.setItems(FXCollections.observableArrayList("Auto Peças Zé", "Posto Ipiranga"));

        // Configurar Tabela
        colIdParcela.setCellValueFactory(new PropertyValueFactory<>("idComposto"));
        colVencimento.setCellValueFactory(new PropertyValueFactory<>("vencimento"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

        tabelaParcelas.setItems(listaParcelas);
    }

    @FXML
    public void handleAdicionarParcela() {
        try {
            double valor = Double.parseDouble(txtValorParcela.getText().replace(",", "."));
            LocalDate ven = dtVencimentoParcela.getValue();

            if (ven == null) {
                System.out.println("Selecione uma data de vencimento");
                return;
            }

            // Simula o ID composto (Ex: PEDIDO_1, PEDIDO_2)
            // No banco real seria o ID do Pedido salvo + numero da parcela
            String idSimulado = "NOVO_" + contadorParcelas++; 

            listaParcelas.add(new Parcela(idSimulado, valor, ven));
            
            // Limpa campos
            txtValorParcela.clear();
            dtVencimentoParcela.setValue(null);
            atualizarTotal();

        } catch (NumberFormatException e) {
            System.out.println("Valor inválido");
        }
    }

    @FXML
    public void handleRemoverParcela() {
        Parcela selecionada = tabelaParcelas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            listaParcelas.remove(selecionada);
            atualizarTotal();
        }
    }

    private void atualizarTotal() {
        double total = 0;
        for (Parcela p : listaParcelas) {
            total += p.getValor();
        }
        lblTotal.setText(String.format("R$ %.2f", total));
    }

    @FXML
    public void handleSalvar() {
        System.out.println("Salvando Conta a Pagar...");
    }

    // Classe Interna para a Tabela
    public static class Parcela {
        private String idComposto;
        private Double valor;
        private LocalDate vencimento;

        public Parcela(String idComposto, Double valor, LocalDate vencimento) {
            this.idComposto = idComposto;
            this.valor = valor;
            this.vencimento = vencimento;
        }

        public String getIdComposto() { return idComposto; }
        public Double getValor() { return valor; }
        public LocalDate getVencimento() { return vencimento; }
    }
}
