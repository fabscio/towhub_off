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
  @FXML private TextField txtValorParcela;
  @FXML private DatePicker dtVencimentoParcela;
  @FXML private Label lblTotal;

  @FXML private TableView<ParcelaPagar> tabelaParcelas;
  @FXML private TableColumn<ParcelaPagar, Integer> colIdParcela;
  @FXML private TableColumn<ParcelaPagar, LocalDate> colVencimento;
  @FXML private TableColumn<ParcelaPagar, Double> colValor;

  private ObservableList<ParcelaPagar> lista = FXCollections.observableArrayList();
  private int count = 1;

  @FXML
  public void initialize() {
    dtCompra.setValue(LocalDate.now());
    colIdParcela.setCellValueFactory(new PropertyValueFactory<>("numero"));
    colVencimento.setCellValueFactory(new PropertyValueFactory<>("vencimento"));
    colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
    tabelaParcelas.setItems(lista);
  }

  @FXML
  public void acaoAdicionarParcela() {
    try {
      double val = Double.parseDouble(txtValorParcela.getText().replace(",", "."));
      lista.add(new ParcelaPagar(count++, dtVencimentoParcela.getValue(), val));
      double total = lista.stream().mapToDouble(ParcelaPagar::getValor).sum();
      lblTotal.setText(String.format("R$ %.2f", total));
    } catch(Exception e) {}
  }

  @FXML
  public void acaoSalvar() {
    ContaPagar c = new ContaPagar();
    c.setDataCompra(dtCompra.getValue());
    c.setIdFornecedor(1); // Mock
    c.setIdBase(1);       // Mock
    for(ParcelaPagar p : lista) c.adicionarParcela(p);

    if(new ContaPagarDAO().salvar(c)) {
      System.out.println("Conta Salva!");
      lista.clear();
    }
  }

  @FXML
    public void acaoRemoverParcela() {
        ParcelaPagar p = tabelaParcelas.getSelectionModel().getSelectedItem();
        if (p != null) {
            // Se você tiver uma lista observável chamada 'listaParcelas' ou 'lista'
            tabelaParcelas.getItems().remove(p);
            // Chame seu método de atualizar total aqui, se houver
            // atualizarTotal();
        }
    }
}
