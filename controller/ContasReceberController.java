package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.LoteCobranca;
import model.dao.ContasReceberDAO;
import java.time.LocalDate;

public class ContasReceberController {

  @FXML private ComboBox<String> cbCliente, cbStatus;
  @FXML private DatePicker dtEmissao, dtVencimento;
  @FXML private Label lblTotalLote;

  // Usamos MockOS apenas para a tabela visual
  @FXML private ComboBox<MockOS> cbOsDisponiveis;
  @FXML private TableView<MockOS> tabelaOs;
  @FXML private TableColumn<MockOS, Integer> colIdOs;
  @FXML private TableColumn<MockOS, Double> colValorOs;

  private ObservableList<MockOS> selecionadas = FXCollections.observableArrayList();

  @FXML
  public void initialize() {
    dtEmissao.setValue(LocalDate.now());
    cbCliente.setItems(FXCollections.observableArrayList("Seguradora Alpha", "Transportadora Beta"));
    cbStatus.setItems(FXCollections.observableArrayList("PENDENTE", "PAGO"));

    // Simula OSs vindas do banco
    ObservableList<MockOS> pendentes = FXCollections.observableArrayList(
      new MockOS(101, 150.00),
      new MockOS(102, 300.00)
    );
    cbOsDisponiveis.setItems(pendentes);

    colIdOs.setCellValueFactory(new PropertyValueFactory<>("id"));
    colValorOs.setCellValueFactory(new PropertyValueFactory<>("valor"));
    tabelaOs.setItems(selecionadas);
  }

  @FXML
  public void acaoAdicionarOs() {
    MockOS os = cbOsDisponiveis.getValue();
    if (os != null && !selecionadas.contains(os)) {
      selecionadas.add(os);
      atualizarTotal();
    }
  }

  @FXML
  public void acaoRemoverOs() {
    MockOS item = tabelaOs.getSelectionModel().getSelectedItem();
    if (item != null) {
      selecionadas.remove(item);
      atualizarTotal();
    }
  }

  private void atualizarTotal() {
    double total = selecionadas.stream().mapToDouble(MockOS::getValor).sum();
    lblTotalLote.setText(String.format("R$ %.2f", total));
  }

  @FXML
  public void acaoSalvar() {
    double total = selecionadas.stream().mapToDouble(MockOS::getValor).sum();

    LoteCobranca lote = new LoteCobranca(1, dtEmissao.getValue(), dtVencimento.getValue(),
                                         cbStatus.getValue(), total);

    for (MockOS m : selecionadas) {
      lote.getIdsOs().add(m.getId());
    }

    if (new ContasReceberDAO().salvar(lote)) {
      System.out.println("Fatura Gerada com Sucesso!");
      selecionadas.clear();
      atualizarTotal();
    }
  }

  // Classe auxiliar interna
  public static class MockOS {
      private int id; private double valor;
      public MockOS(int id, double valor) { this.id=id; this.valor=valor; }
      public int getId() { return id; }
      public double getValor() { return valor; }
      public String toString() { return "OS #" + id + " - R$ " + valor; }
  }
}
