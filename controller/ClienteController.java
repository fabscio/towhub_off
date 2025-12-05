package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Base;
import model.Cliente;
import model.dao.BaseDAO;
import model.dao.ClienteDAO;
import util.Alerta;
import util.Documento; // Importando a nova classe
import java.util.List;

public class ClienteController {

  @FXML private RadioButton rbPF;
  @FXML private RadioButton rbPJ;
  @FXML private ToggleGroup grupoTipoCliente;

  @FXML private TextField txtNome;
  @FXML private TextField txtTelefone;
  @FXML private TextField txtEmail;
  @FXML private TextField txtEndereco;

  @FXML private ComboBox<Base> cbBase;

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
    try {
        // 1. Validação Básica
        if (txtNome.getText().isEmpty()) {
            Alerta.mostrarErro("Erro", "O Nome/Razão Social é obrigatório.");
            return;
        }
        if (cbBase.getValue() == null) {
            Alerta.mostrarErro("Erro", "Selecione uma base de atendimento!");
            return;
        }

        // 2. Captura e Limpeza de Dados
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();
        String endereco = txtEndereco.getText();
        String gestor = txtGestor.getText();
        int idBase = cbBase.getValue().getId();

        String tipo;
        String documentoBruto;

        if (rbPF.isSelected()) {
            tipo = "PF";
            documentoBruto = txtCpf.getText();
        } else {
            tipo = "PJ";
            documentoBruto = txtCnpj.getText();
        }

        // --- CORREÇÃO: Limpa o documento aqui ---
        String documentoLimpo = Documento.limpar(documentoBruto);

        // Opcional: Validar tamanho
        if ("PF".equals(tipo) && documentoLimpo.length() != 11) {
             Alerta.mostrarErro("Erro", "CPF inválido (deve conter 11 dígitos).");
             return;
        }
        if ("PJ".equals(tipo) && documentoLimpo.length() != 14) {
             Alerta.mostrarErro("Erro", "CNPJ inválido (deve conter 14 dígitos).");
             return;
        }

        // 3. Cria o objeto e Salva
        Cliente cliente = new Cliente(tipo, nome, documentoLimpo, gestor, telefone, email, endereco, idBase);
        ClienteDAO dao = new ClienteDAO();

        if (dao.salvar(cliente)) {
            Alerta.mostrarSucesso("Sucesso", "Cliente cadastrado com sucesso!");
            limparCampos();
        } else {
            Alerta.mostrarErro("Erro", "Falha ao cadastrar cliente (Verifique se o CPF/CNPJ já existe).");
        }

    } catch (Exception e) {
        e.printStackTrace();
        Alerta.mostrarErro("Erro Crítico", e.getMessage());
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
