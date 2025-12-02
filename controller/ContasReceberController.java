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
  @FXML private ComboBox<ModeloOSSimples> cbOsDisponiveis;
  
  @FXML private TableView<ModeloOSSimples> tabelaOs;
  @FXML private TableColumn<ModeloOSSimples, String> colIdOs;
  @FXML private TableColumn<ModeloOSSimples, Double> colValorOs;

  private ObservableList<ModeloOSSimples> listaOsSelecionadas = FXCollections.observableArrayList();

  @FXML
  public void initialize() {
    cbCliente.setItems(FXCollections.observableArrayList("Seguradora Alpha", "Transportadora Beta"));
    cbStatus.setItems(FXCollections.observableArrayList("PENDENTE", "PAGO"));
    
    // Simula OSs vindas do banco (OSs que ainda não tem id_lote)
    ObservableList<ModeloOSSimples> pendentes = FXCollections.observableArrayList(
      new ModeloOSSimples(101, 150.00),
      new ModeloOSSimples(102, 300.00)
    );
    cbOsDisponiveis.setItems(pendentes);

    colIdOs.setCellValueFactory(new PropertyValueFactory<>("id"));
    colValorOs.setCellValueFactory(new PropertyValueFactory<>("valor"));
    tabelaOs.setItems(listaOsSelecionadas);
  }

  @FXML
  public void acaoAdicionarOs() {
    ModeloOSSimples os = cbOsDisponiveis.getValue();
    if (os != null && !listaOsSelecionadas.contains(os)) {
      listaOsSelecionadas.add(os);
      atualizarTotal();
    }
  }
  
  @FXML public void acaoRemoverOs() { /* Lógica similar ao adicionar */ }

  private void atualizarTotal() {
    double total = listaOsSelecionadas.stream().mapToDouble(ModeloOSSimples::getValor).sum();
    lblTotalLote.setText(String.format("R$ %.2f", total));
  }

  @FXML
  public void acaoSalvar() {
    double total = listaOsSelecionadas.stream().mapToDouble(ModeloOSSimples::getValor).sum();
    
    LoteCobranca lote = new LoteCobranca(1, dtEmissao.getValue(), dtVencimento.getValue(), 
                                         cbStatus.getValue(), total);
    
    for (ModeloOSSimples os : listaOsSelecionadas) {
      lote.getIdsOrdensServico().add(os.getId());
    }

    if (new ContasReceberDAO().salvar(lote)) {
      System.out.println("Fatura Gerada com Sucesso!");
      listaOsSelecionadas.clear();
    }
  }
  
  // Classe auxiliar interna apenas para preencher a tabela
  public static class ModeloOSSimples {
    private int id; private double valor;
    public ModeloOSSimples(int id, double valor) { this.id = id; this.valor = valor; }
    public int getId() { return id; }
    public double getValor() { return valor; }
    public String toString() { return "OS #" + id + " - R$ " + valor; }
  }
}
