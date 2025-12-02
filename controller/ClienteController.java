package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Base;
import model.Cliente;
import model.dao.BaseDAO;
import model.dao.ClienteDAO;
import java.util.List;

public class ClienteController {

  // --- Elementos da Interface Gráfica ---
  @FXML private RadioButton rbPF;
  @FXML private RadioButton rbPJ;
  @FXML private ToggleGroup grupoTipoCliente;

  @FXML private TextField txtNome;
  @FXML private TextField txtTelefone;
  @FXML private TextField txtEmail;
  @FXML private TextField txtEndereco;

  @FXML private ComboBox<Base> cbBase; // Agora usa o objeto Base corretamente

  @FXML private TextField txtCpf;
  @FXML private TextField txtCnpj;
  @FXML private TextField txtGestor;

  @FXML private Label lblCpf;
  @FXML private Label lblCnpj;
  @FXML private Label lblGestor;

  @FXML
  public void initialize() {
    carregarBasesDoBanco();
    configurarMudancaDeTipo();
    atualizarVisibilidade(false);
  }

  private void carregarBasesDoBanco() {
    BaseDAO dao = new BaseDAO();
    List<Base> listaBases = dao.listar();
    cbBase.setItems(FXCollections.observableArrayList(listaBases));
  }

  private void configurarMudancaDeTipo() {
    grupoTipoCliente.selectedToggleProperty().addListener((obs, antigo, novo) -> {
      atualizarVisibilidade(rbPJ.isSelected());
    });
  }

  private void atualizarVisibilidade(boolean ehPessoaJuridica) {
    boolean ehFisica = !ehPessoaJuridica;

    lblCpf.setVisible(ehFisica);
    txtCpf.setVisible(ehFisica);
    lblCpf.setManaged(ehFisica);
    txtCpf.setManaged(ehFisica);

    lblCnpj.setVisible(ehPessoaJuridica);
    txtCnpj.setVisible(ehPessoaJuridica);
    lblCnpj.setManaged(ehPessoaJuridica);
    txtCnpj.setManaged(ehPessoaJuridica);

    lblGestor.setVisible(ehPessoaJuridica);
    txtGestor.setVisible(ehPessoaJuridica);
    lblGestor.setManaged(ehPessoaJuridica);
    txtGestor.setManaged(ehPessoaJuridica);
  }

  @FXML
  public void acaoSalvar() {
    String nome = txtNome.getText();
    String telefone = txtTelefone.getText();
    String email = txtEmail.getText();
    String endereco = txtEndereco.getText();

    Base baseSelecionada = cbBase.getValue();
    if (baseSelecionada == null) {
      System.out.println("Aviso: Selecione uma base de atendimento!");
      return;
    }
    int idBase = baseSelecionada.getId();

    String tipo;
    String documento;

    if (rbPF.isSelected()) {
      tipo = "PF";
      documento = txtCpf.getText();
    } else {
      tipo = "PJ";
      documento = txtCnpj.getText();
    }

    Cliente cliente = new Cliente(tipo, nome, documento, telefone, email, endereco, idBase);
    ClienteDAO dao = new ClienteDAO();

    if (dao.salvar(cliente)) {
      System.out.println("SUCESSO: Cliente cadastrado com sucesso!");
      limparCampos();
    } else {
      System.out.println("ERRO: Falha ao cadastrar cliente.");
    }
  }

  private void limparCampos() {
    txtNome.clear(); txtTelefone.clear(); txtEmail.clear();
    txtEndereco.clear(); txtCpf.clear(); txtCnpj.clear();
    txtGestor.clear();
    cbBase.getSelectionModel().clearSelection();
    txtNome.requestFocus();
  }
}
