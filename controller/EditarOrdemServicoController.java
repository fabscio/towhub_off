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

public class EditarOrdemServicoController {

  @FXML private TextField txtPesquisaId;
  
  // Campos do Formulário
  @FXML private TextField txtId;
  @FXML private DatePicker dtData;
  @FXML private ComboBox<String> cbCliente, cbBase, cbMotorista; 
  @FXML private TextField txtVeiculo, txtPlaca, txtContato;
  
  // Tabela
  @FXML private TableView<ItemOrdemServico> tabelaServicos;
  @FXML private TableColumn<ItemOrdemServico, String> colServico;
  @FXML private TableColumn<ItemOrdemServico, Integer> colQtd;
  @FXML private TableColumn<ItemOrdemServico, Double> colValor;
  @FXML private TableColumn<ItemOrdemServico, Double> colSubtotal;
  @FXML private Label lblValorTotal;

  private ObservableList<ItemOrdemServico> listaItens = FXCollections.observableArrayList();
  private OrdemServico osAtual = null; // Guarda a OS que está sendo editada

  @FXML
  public void initialize() {
    // Simulação de preenchimento dos combos
    cbCliente.setItems(FXCollections.observableArrayList("Seguradora Alpha", "Cliente Particular"));
    cbBase.setItems(FXCollections.observableArrayList("Matriz", "Filial Norte"));

    configurarTabela();
  }

  private void configurarTabela() {
    colServico.setCellValueFactory(new PropertyValueFactory<>("nomeServico"));
    colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
    colValor.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
    colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
    tabelaServicos.setItems(listaItens);
  }

  @FXML
  public void acaoPesquisar() {
    String idTexto = txtPesquisaId.getText();
    if (idTexto.isEmpty()) return;

    try {
      int id = Integer.parseInt(idTexto);
      OrdemServicoDAO dao = new OrdemServicoDAO();
      this.osAtual = dao.buscarPorId(id);

      if (osAtual != null) {
        preencherFormulario(osAtual);
      } else {
        System.out.println("OS não encontrada.");
        limparFormulario();
      }
    } catch (NumberFormatException e) {
      System.out.println("Digite um número válido.");
    }
  }

  private void preencherFormulario(OrdemServico os) {
    txtId.setText(String.valueOf(os.getId()));
    dtData.setValue(os.getDataEmissao());
    txtVeiculo.setText(os.getVeiculoModelo());
    txtPlaca.setText(os.getVeiculoPlaca());
    txtContato.setText(os.getContato());
    
    // Preencher Combos (Simplificado: Na prática deve selecionar pelo ID)
    cbBase.getSelectionModel().select(0); 

    // Preencher Tabela
    listaItens.setAll(os.getItens());
    atualizarTotal();
  }

  @FXML
  public void acaoAdicionarServico() {
    // Adiciona item novo na lista existente
    ItemOrdemServico item = new ItemOrdemServico(1, "Serviço Adicional", 1, 100.00);
    listaItens.add(item);
    atualizarTotal();
  }

  @FXML
  public void acaoRemoverServico() {
    ItemOrdemServico item = tabelaServicos.getSelectionModel().getSelectedItem();
    if (item != null) {
      listaItens.remove(item);
      atualizarTotal();
    }
  }

  private void atualizarTotal() {
    double total = listaItens.stream().mapToDouble(ItemOrdemServico::getSubtotal).sum();
    lblValorTotal.setText(String.format("R$ %.2f", total));
  }

  @FXML
  public void acaoSalvarAlteracoes() {
    if (osAtual == null) return;

    // Atualiza objeto com dados da tela
    osAtual.setDataEmissao(dtData.getValue());
    osAtual.setVeiculoModelo(txtVeiculo.getText());
    osAtual.setVeiculoPlaca(txtPlaca.getText());
    osAtual.setContato(txtContato.getText());
    
    // Atualiza lista de itens
    osAtual.getItens().clear();
    osAtual.getItens().addAll(listaItens);
    osAtual.setValorTotal(listaItens.stream().mapToDouble(ItemOrdemServico::getSubtotal).sum());

    OrdemServicoDAO dao = new OrdemServicoDAO();
    if (dao.atualizar(osAtual)) {
      System.out.println("OS Atualizada com Sucesso!");
    } else {
      System.out.println("Erro ao atualizar.");
    }
  }
  
  private void limparFormulario() {
      txtId.clear(); txtVeiculo.clear(); txtPlaca.clear();
      listaItens.clear(); lblValorTotal.setText("R$ 0,00");
  }
}
