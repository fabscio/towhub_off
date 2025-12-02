package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Fornecedor;
import model.dao.FornecedorDAO;

public class FornecedorController {

  @FXML private RadioButton rbPF, rbPJ;
  @FXML private ToggleGroup grupoTipoFornecedor;

  @FXML private TextField txtNome, txtNicho, txtTelefone, txtEmail, txtEndereco;

  // Campos dinâmicos
  @FXML private TextField txtCpf, txtCnpj;
  @FXML private Label lblCpf, lblCnpj;

  @FXML
  public void initialize() {
    // Listener para troca de PF/PJ
    grupoTipoFornecedor.selectedToggleProperty().addListener((obs, o, n) -> {
      atualizarVisibilidade(rbPJ.isSelected());
    });
    atualizarVisibilidade(false);
  }

  private void atualizarVisibilidade(boolean ehPJ) {
    lblCpf.setVisible(!ehPJ); txtCpf.setVisible(!ehPJ);
    lblCpf.setManaged(!ehPJ); txtCpf.setManaged(!ehPJ);

    lblCnpj.setVisible(ehPJ); txtCnpj.setVisible(ehPJ);
    lblCnpj.setManaged(ehPJ); txtCnpj.setManaged(ehPJ);
  }

  @FXML
  public void acaoSalvar() {
    String nome = txtNome.getText();
    String documento = rbPF.isSelected() ? txtCpf.getText() : txtCnpj.getText();
    String tipo = rbPF.isSelected() ? "PF" : "PJ";

    Fornecedor f = new Fornecedor(tipo, nome, documento, txtNicho.getText(),
                                  txtTelefone.getText(), txtEmail.getText(), txtEndereco.getText());

    FornecedorDAO dao = new FornecedorDAO();
    if (dao.salvar(f)) {
      System.out.println("Fornecedor salvo com sucesso!");
      limparCampos();
    }
  }

  private void limparCampos() {
    txtNome.clear(); txtNicho.clear(); txtTelefone.clear(); txtEmail.clear();
    txtEndereco.clear(); txtCpf.clear(); txtCnpj.clear();
  }
}
