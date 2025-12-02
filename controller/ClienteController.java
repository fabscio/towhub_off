package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Cliente;
import model.dao.ClienteDAO;

public class ClienteController {

  // --- Elementos da Interface Gráfica ---

  @FXML private RadioButton rbPF;
  @FXML private RadioButton rbPJ;
  @FXML private ToggleGroup grupoTipoCliente;

  @FXML private TextField txtNome;
  @FXML private TextField txtTelefone;
  @FXML private TextField txtEmail;
  @FXML private TextField txtEndereco;

  @FXML private ComboBox<String> cbBase;

  // Campos que mudam conforme o tipo (PF ou PJ)
  @FXML private TextField txtCpf;
  @FXML private TextField txtCnpj;
  @FXML private TextField txtGestor;

  @FXML private Label lblCpf;
  @FXML private Label lblCnpj;
  @FXML private Label lblGestor;

  /**
   * Método executado automaticamente ao abrir a tela.
   */
  @FXML
  public void initialize() {
    carregarBases();
    configurarMudancaDeTipo();

    // Inicia a tela no modo Pessoa Física
    atualizarVisibilidade(false);
  }

  private void carregarBases() {
    // Futuramente, isso virá do banco de dados (BaseDAO)
    cbBase.setItems(FXCollections.observableArrayList("Matriz", "Filial Norte"));
  }

  private void configurarMudancaDeTipo() {
    grupoTipoCliente.selectedToggleProperty().addListener((obs, antigo, novo) -> {
      // Se o botão PJ estiver selecionado, passa true
      atualizarVisibilidade(rbPJ.isSelected());
    });
  }

  /**
   * Controla quais campos aparecem na tela (PF ou PJ).
   */
  private void atualizarVisibilidade(boolean ehPessoaJuridica) {

    // Configuração para Pessoa Física (CPF)
    boolean ehFisica = !ehPessoaJuridica;

    lblCpf.setVisible(ehFisica);
    txtCpf.setVisible(ehFisica);
    lblCpf.setManaged(ehFisica); // setManaged define se o campo ocupa espaço na tela
    txtCpf.setManaged(ehFisica);

    // Configuração para Pessoa Jurídica (CNPJ e Gestor)
    lblCnpj.setVisible(ehPessoaJuridica);
    txtCnpj.setVisible(ehPessoaJuridica);
    lblCnpj.setManaged(ehPessoaJuridica);
    txtCnpj.setManaged(ehPessoaJuridica);

    lblGestor.setVisible(ehPessoaJuridica);
    txtGestor.setVisible(ehPessoaJuridica);
    lblGestor.setManaged(ehPessoaJuridica);
    txtGestor.setManaged(ehPessoaJuridica);
  }

  /**
   * Ação do botão "Salvar Cliente".
   */
  @FXML
  public void acaoSalvar() {

    // 1. Coleta os dados básicos
    String nome = txtNome.getText();
    String telefone = txtTelefone.getText();
    String email = txtEmail.getText();
    String endereco = txtEndereco.getText();

    // 2. Validação básica
    if (cbBase.getValue() == null) {
      System.out.println("Aviso: Selecione uma base de atendimento!");
      return;
    }

    // Simulação: Converte o nome da base para ID (1 ou 2)
    int idBase = cbBase.getValue().equals("Matriz") ? 1 : 2;

    // 3. Define dados específicos (PF/PJ)
    String tipo;
    String documento;

    if (rbPF.isSelected()) {
      tipo = "PF";
      documento = txtCpf.getText();
    } else {
      tipo = "PJ";
      documento = txtCnpj.getText();
    }

    // 4. Cria o objeto Modelo
    Cliente cliente = new Cliente(tipo, nome, documento, telefone, email, endereco, idBase);

    // 5. Chama o DAO para persistir no banco
    ClienteDAO dao = new ClienteDAO();

    if (dao.salvar(cliente)) {
      System.out.println("SUCESSO: Cliente cadastrado com sucesso!");
      limparCampos();
    } else {
      System.out.println("ERRO: Falha ao cadastrar cliente.");
    }
  }

  private void limparCampos() {
    txtNome.clear();
    txtTelefone.clear();
    txtEmail.clear();
    txtEndereco.clear();
    txtCpf.clear();
    txtCnpj.clear();
    txtGestor.clear();
    // Opcional: Voltar o foco para o nome
    txtNome.requestFocus();
  }

  // Em controller/ClienteController.java

@FXML private ComboBox<Base> cbBase; // Note: Tipo <Base>, não <String>

@FXML
public void initialize() {
    BaseDAO dao = new BaseDAO();
    List<Base> basesDoBanco = dao.listar();

    cbBase.setItems(FXCollections.observableArrayList(basesDoBanco));
}

@FXML
public void acaoSalvar() {
    // ...
    Base baseSelecionada = cbBase.getValue();
    int idBase = baseSelecionada.getId(); // Agora temos o ID real do banco!
    // ...
}
}
