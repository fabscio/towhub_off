package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ContaPagar;
import model.ParcelaPagar;
import model.dao.ContaPagarDAO;
import java.time.LocalDate;

public class ContasPagarController {

  @FXML private DatePicker dtCompra;
  @FXML private ComboBox<String> cbBase, cbFornecedor;
  @FXML private TextField txtValorParcela;
  @FXML private DatePicker dtVencimentoParcela;
  @FXML private Label lblTotal;

  @FXML private TableView<ParcelaPagar> tabelaParcelas;
  @FXML private TableColumn<ParcelaPagar, Integer> colIdParcela; // Usando numeroParcela visualmente
  @FXML private TableColumn<ParcelaPagar, LocalDate> colVencimento;
  @FXML private TableColumn<ParcelaPagar, Double> colValor;

  private ObservableList<ParcelaPagar> listaParcelas = FXCollections.observableArrayList();
  private int contadorParcela = 1;

  @FXML
  public void initialize() {
    // Simulação de Dados (Use FornecedorDAO.listar() depois)
    cbBase.setItems(FXCollections.observableArrayList("Matriz", "Filial Norte"));
    cbFornecedor.setItems(FXCollections.observableArrayList("Auto Peças Zé", "Posto Ipiranga"));
    dtCompra.setValue(LocalDate.now());

    colIdParcela.setCellValueFactory(new PropertyValueFactory<>("numeroParcela"));
    colVencimento.setCellValueFactory(new PropertyValueFactory<>("vencimento"));
    colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
    
    tabelaParcelas.setItems(listaParcelas);
  }

  @FXML
  public void acaoAdicionarParcela() {
    try {
      double valor = Double.parseDouble(txtValorParcela.getText().replace(",", "."));
      LocalDate ven = dtVencimentoParcela.getValue();
      
      if (ven == null) return;

      listaParcelas.add(new ParcelaPagar(contadorParcela++, ven, valor));
      atualizarTotal();
      
    } catch (NumberFormatException e) { System.out.println("Valor inválido"); }
  }

  @FXML
  public void acaoRemoverParcela() {
    ParcelaPagar p = tabelaParcelas.getSelectionModel().getSelectedItem();
    if (p != null) {
      listaParcelas.remove(p);
      atualizarTotal();
    }
  }

  private void atualizarTotal() {
    double total = listaParcelas.stream().mapToDouble(ParcelaPagar::getValor).sum();
    lblTotal.setText(String.format("R$ %.2f", total));
  }

  @FXML
  public void acaoSalvar() {
    ContaPagar conta = new ContaPagar();
    conta.setIdFornecedor(1); // Simulação: Pegar do ID do item selecionado no ComboBox
    conta.setIdBase(1);       // Simulação
    conta.setDataCompra(dtCompra.getValue());
    
    for (ParcelaPagar p : listaParcelas) {
      conta.adicionarParcela(p);
    }
    
    if (new ContaPagarDAO().salvar(conta)) {
      System.out.println("Compra registrada com sucesso!");
      listaParcelas.clear();
      atualizarTotal();
    }
  }
}
