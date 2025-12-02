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

  // Cabeçalho
  @FXML private DatePicker dtData;
  @FXML private ComboBox<String> cbCliente; // Deveria ser ComboBox<Cliente>
  @FXML private ComboBox<String> cbBase;
  @FXML private Label lblValorTotal;

  // Tabela de Itens
  @FXML private TableView<ItemOrdemServico> tabelaServicos;
  @FXML private TableColumn<ItemOrdemServico, String> colServico;
  @FXML private TableColumn<ItemOrdemServico, Integer> colQtd;
  @FXML private TableColumn<ItemOrdemServico, Double> colValor;
  @FXML private TableColumn<ItemOrdemServico, Double> colSubtotal;

  private ObservableList<ItemOrdemServico> listaItens = FXCollections.observableArrayList();

  @FXML
  public void initialize() {
    // Simulação de Dados
    cbCliente.setItems(FXCollections.observableArrayList("Seguradora Alpha", "Cliente Particular"));
    cbBase.setItems(FXCollections.observableArrayList("Matriz", "Filial Norte"));
    dtData.setValue(LocalDate.now());

    // Configura Tabela
    colServico.setCellValueFactory(new PropertyValueFactory<>("nomeServico"));
    colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
    colValor.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
    colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

    tabelaServicos.setItems(listaItens);
  }

  @FXML
  public void handleAdicionarServico() {
    // Simulação: Adiciona um item fixo (Futuramente abrirá um popup de busca)
    ItemOrdemServico item = new ItemOrdemServico(1, "Guincho Leve", 1, 150.00);
    listaItens.add(item);
    atualizarTotal();
  }

  @FXML
  public void handleRemoverServico() {
    ItemOrdemServico selecionado = tabelaServicos.getSelectionModel().getSelectedItem();
    if (selecionado != null) {
      listaItens.remove(selecionado);
      atualizarTotal();
    }
  }

  private void atualizarTotal() {
    double total = listaItens.stream().mapToDouble(ItemOrdemServico::getSubtotal).sum();
    lblValorTotal.setText(String.format("R$ %.2f", total));
  }

  @FXML
  public void handleConcluir() {
    // 1. Monta a OS
    OrdemServico os = new OrdemServico();
    os.setDataEmissao(dtData.getValue());
    os.setIdCliente(1); // Simulação (pegar do ComboBox)
    os.setIdBase(1);    // Simulação

    // 2. Adiciona os itens da tabela na OS
    for (ItemOrdemServico item : listaItens) {
      os.adicionarItem(item);
    }

    // 3. Salva no Banco (com transação)
    OrdemServicoDAO dao = new OrdemServicoDAO();
    if (dao.salvar(os)) {
      System.out.println("Ordem de Serviço finalizada com sucesso!");
      listaItens.clear();
      atualizarTotal();
    } else {
      System.out.println("Erro ao finalizar OS.");
    }
  }
}
