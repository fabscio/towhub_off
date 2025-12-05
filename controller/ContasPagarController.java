package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Base;
import model.ContaPagar;
import model.Fornecedor;
import model.ParcelaPagar;
import model.dao.BaseDAO;
import model.dao.ContaPagarDAO;
import model.dao.FornecedorDAO;
import util.Alerta;
import java.time.LocalDate;

public class ContasPagarController {

  @FXML private DatePicker dtCompra;
  @FXML private ComboBox<Base> cbBase;
  @FXML private ComboBox<Fornecedor> cbFornecedor;
  @FXML private TextField txtValorParcela;
  @FXML private DatePicker dtVencimentoParcela;
  @FXML private Label lblTotal;

  @FXML private TableView<ParcelaPagar> tabelaParcelas;
  @FXML private TableColumn<ParcelaPagar, Integer> colIdParcela;
  @FXML private TableColumn<ParcelaPagar, LocalDate> colVencimento;
  @FXML private TableColumn<ParcelaPagar, Double> colValor;

  private ObservableList<ParcelaPagar> listaParcelas = FXCollections.observableArrayList();
  private int contadorParcela = 1;

  @FXML
  public void initialize() {
    // CARREGA DO BANCO
    cbBase.setItems(FXCollections.observableArrayList(new BaseDAO().listar()));
    cbFornecedor.setItems(FXCollections.observableArrayList(new FornecedorDAO().listar()));

    dtCompra.setValue(LocalDate.now());

    colIdParcela.setCellValueFactory(new PropertyValueFactory<>("numero"));
    colVencimento.setCellValueFactory(new PropertyValueFactory<>("vencimento"));
    colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

    tabelaParcelas.setItems(listaParcelas);
  }

  @FXML
  public void acaoAdicionarParcela() {
    try {
      double valor = Double.parseDouble(txtValorParcela.getText().replace(",", "."));
      LocalDate ven = dtVencimentoParcela.getValue();

      if (ven == null) { Alerta.mostrarErro("Erro", "Defina a data."); return; }

      listaParcelas.add(new ParcelaPagar(contadorParcela++, ven, valor));
      atualizarTotal();

    } catch (NumberFormatException e) { Alerta.mostrarErro("Erro", "Valor inválido"); }
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
    if(cbFornecedor.getValue() == null || cbBase.getValue() == null) {
        Alerta.mostrarErro("Erro", "Selecione Base e Fornecedor.");
        return;
    }

    ContaPagar conta = new ContaPagar();
    conta.setIdFornecedor(cbFornecedor.getValue().getId()); // ID Real
    conta.setIdBase(cbBase.getValue().getId());             // ID Real
    conta.setDataCompra(dtCompra.getValue());

    for (ParcelaPagar p : listaParcelas) {
      conta.adicionarParcela(p);
    }

    if (new ContaPagarDAO().salvar(conta)) {
      Alerta.mostrarSucesso("Sucesso", "Compra registrada!");
      listaParcelas.clear();
      atualizarTotal();
      contadorParcela = 1;
    }
  }
}
