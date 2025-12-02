package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OrdemServico;
import model.ItemOrdemServico;
import model.dao.OrdemServicoDAO;
import java.time.LocalDate;

public class OrdemServicoController {
  @FXML private DatePicker dtData;
  @FXML private ComboBox<String> cbCliente, cbBase;
  @FXML private TextField txtVeiculo, txtPlaca, txtContato;
  @FXML private Label lblValorTotal;

  @FXML private TableView<ItemOrdemServico> tabelaServicos;
  @FXML private TableColumn<ItemOrdemServico, String> colServico;
  @FXML private TableColumn<ItemOrdemServico, Integer> colQtd;
  @FXML private TableColumn<ItemOrdemServico, Double> colValor;
  @FXML private TableColumn<ItemOrdemServico, Double> colSubtotal;

  private ObservableList<ItemOrdemServico> itens = FXCollections.observableArrayList();

  @FXML
  public void initialize() {
    dtData.setValue(LocalDate.now());
    // Preencha os combos com DAO.listar() depois
    cbCliente.setItems(FXCollections.observableArrayList("Cliente A", "Cliente B"));
    cbBase.setItems(FXCollections.observableArrayList("Matriz"));

    colServico.setCellValueFactory(new PropertyValueFactory<>("nomeServico"));
    colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
    colValor.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
    colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
    tabelaServicos.setItems(itens);
  }

  @FXML
  public void handleAdicionarServico() {
    itens.add(new ItemOrdemServico(1, "Guincho Padrão", 1, 150.00));
    atualizarTotal();
  }

  @FXML
  public void handleRemoverServico() {
    ItemOrdemServico i = tabelaServicos.getSelectionModel().getSelectedItem();
    if (i != null) itens.remove(i);
    atualizarTotal();
  }

  private void atualizarTotal() {
    double total = itens.stream().mapToDouble(ItemOrdemServico::getSubtotal).sum();
    lblValorTotal.setText(String.format("R$ %.2f", total));
  }

  @FXML
  public void handleConcluir() {
    OrdemServico os = new OrdemServico();
    os.setDataEmissao(dtData.getValue());
    os.setIdCliente(1); // Mock
    os.setIdBase(1);    // Mock
    os.setVeiculoModelo(txtVeiculo.getText());
    os.setVeiculoPlaca(txtPlaca.getText());
    os.setContato(txtContato.getText());

    for (ItemOrdemServico i : itens) os.adicionarItem(i);

    if (new OrdemServicoDAO().salvar(os)) {
      System.out.println("OS Salva!");
      itens.clear();
      atualizarTotal();
    }
  }
}
