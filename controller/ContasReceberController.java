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
  @FXML private DatePicker dtEmissao, dtVencimento;
  @FXML private Label lblTotalLote;
  @FXML private ComboBox<String> cbOsDisponiveis; // Na pratica use objetos OS
  @FXML private TableView<MockOS> tabelaOs;
  @FXML private TableColumn<MockOS, Integer> colIdOs;
  @FXML private TableColumn<MockOS, Double> colValorOs;

  private ObservableList<MockOS> selecionadas = FXCollections.observableArrayList();

  @FXML
  public void initialize() {
    dtEmissao.setValue(LocalDate.now());
    cbOsDisponiveis.setItems(FXCollections.observableArrayList("OS 100 - R$ 500", "OS 101 - R$ 200"));

    colIdOs.setCellValueFactory(new PropertyValueFactory<>("id"));
    colValorOs.setCellValueFactory(new PropertyValueFactory<>("valor"));
    tabelaOs.setItems(selecionadas);
  }

  @FXML
  public void acaoAdicionarOs() {
    // Mock simplificado. Na real, pegue o objeto do combo
    selecionadas.add(new MockOS(100, 500.00));
    double total = selecionadas.stream().mapToDouble(MockOS::getValor).sum();
    lblTotalLote.setText(String.format("R$ %.2f", total));
  }

  @FXML
  public void acaoSalvar() {
    double total = selecionadas.stream().mapToDouble(MockOS::getValor).sum();
    LoteCobranca lote = new LoteCobranca(1, dtEmissao.getValue(), dtVencimento.getValue(), "PENDENTE", total);
    for(MockOS m : selecionadas) lote.getIdsOs().add(m.getId());

    if(new ContasReceberDAO().salvar(lote)) {
      System.out.println("Lote Gerado!");
      selecionadas.clear();
    }
  }

  @FXML
    public void acaoRemoverOs() {
        // Se você usou uma classe Mock ou Objeto real, ajuste o tipo aqui
        Object itemSelecionado = tabelaOs.getSelectionModel().getSelectedItem();

        if (itemSelecionado != null) {
            tabelaOs.getItems().remove(itemSelecionado);
            // atualizarTotal(); // Se tiver cálculo de total
        }
    }

  public static class MockOS {
      int id; double valor;
      public MockOS(int id, double valor) { this.id=id; this.valor=valor; }
      public int getId() { return id; }
      public double getValor() { return valor; }
  }


}
