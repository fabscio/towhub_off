package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Base;
import model.Cliente;
import model.OrdemServico;
import model.ItemOrdemServico;
import model.dao.BaseDAO;
import model.dao.ClienteDAO;
import model.dao.OrdemServicoDAO;
import util.Alerta;

public class EditarOrdemServicoController {

  @FXML private TextField txtPesquisaId;
  @FXML private TextField txtId;
  @FXML private DatePicker dtData;

  // Combos Tipados
  @FXML private ComboBox<Cliente> cbCliente;
  @FXML private ComboBox<Base> cbBase;

  @FXML private TextField txtVeiculo, txtPlaca, txtContato;

  @FXML private TableView<ItemOrdemServico> tabelaServicos;
  @FXML private TableColumn<ItemOrdemServico, String> colServico;
  @FXML private TableColumn<ItemOrdemServico, Integer> colQtd;
  @FXML private TableColumn<ItemOrdemServico, Double> colValor;
  @FXML private TableColumn<ItemOrdemServico, Double> colSubtotal;
  @FXML private Label lblValorTotal;

  private ObservableList<ItemOrdemServico> listaItens = FXCollections.observableArrayList();
  private OrdemServico osAtual = null;

  @FXML
  public void initialize() {
    // CARREGA DADOS REAIS
    cbCliente.setItems(FXCollections.observableArrayList(new ClienteDAO().listar()));
    cbBase.setItems(FXCollections.observableArrayList(new BaseDAO().listar()));

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
        Alerta.mostrarErro("Aviso", "OS não encontrada.");
        limparFormulario();
      }
    } catch (NumberFormatException e) {
      Alerta.mostrarErro("Erro", "Digite um número válido.");
    }
  }

  private void preencherFormulario(OrdemServico os) {
    txtId.setText(String.valueOf(os.getId()));
    dtData.setValue(os.getDataEmissao());
    txtVeiculo.setText(os.getVeiculoModelo());
    txtPlaca.setText(os.getVeiculoPlaca());
    txtContato.setText(os.getContato());

    // Seleciona Base e Cliente corretos buscando pelo ID na lista do ComboBox
    cbBase.getItems().stream()
        .filter(b -> b.getId() == os.getIdBase())
        .findFirst().ifPresent(cbBase.getSelectionModel()::select);

    cbCliente.getItems().stream()
        .filter(c -> c.getId() == os.getIdCliente())
        .findFirst().ifPresent(cbCliente.getSelectionModel()::select);

    listaItens.setAll(os.getItens());
    atualizarTotal();
  }

  // ... (Mantenha os métodos acaoAdicionarServico, acaoRemoverServico e atualizarTotal iguais) ...
  @FXML
  public void acaoAdicionarServico() {
      // (Lógica igual, ou implemente o Popup igual ao CriarOSController)
      ItemOrdemServico item = new ItemOrdemServico(1, "Serviço Adicional", 1, 100.00);
      listaItens.add(item);
      atualizarTotal();
  }

  @FXML
  public void acaoRemoverServico() {
      ItemOrdemServico item = tabelaServicos.getSelectionModel().getSelectedItem();
      if(item != null) { listaItens.remove(item); atualizarTotal(); }
  }

  private void atualizarTotal() {
      double total = listaItens.stream().mapToDouble(ItemOrdemServico::getSubtotal).sum();
      lblValorTotal.setText(String.format("R$ %.2f", total));
  }

  @FXML
  public void acaoSalvarAlteracoes() {
    if (osAtual == null) return;

    osAtual.setDataEmissao(dtData.getValue());
    osAtual.setVeiculoModelo(txtVeiculo.getText());
    osAtual.setVeiculoPlaca(txtPlaca.getText());
    osAtual.setContato(txtContato.getText());

    if(cbCliente.getValue() != null) osAtual.setIdCliente(cbCliente.getValue().getId());
    if(cbBase.getValue() != null) osAtual.setIdBase(cbBase.getValue().getId());

    osAtual.getItens().clear();
    osAtual.getItens().addAll(listaItens);
    osAtual.setValorTotal(listaItens.stream().mapToDouble(ItemOrdemServico::getSubtotal).sum());

    OrdemServicoDAO dao = new OrdemServicoDAO();
    if (dao.atualizar(osAtual)) {
      Alerta.mostrarSucesso("Sucesso", "OS Atualizada!");
    } else {
      Alerta.mostrarErro("Erro", "Erro ao atualizar.");
    }
  }

  private void limparFormulario() {
      txtId.clear(); txtVeiculo.clear(); txtPlaca.clear();
      listaItens.clear(); lblValorTotal.setText("R$ 0,00");
      cbBase.getSelectionModel().clearSelection();
      cbCliente.getSelectionModel().clearSelection();
  }
}
