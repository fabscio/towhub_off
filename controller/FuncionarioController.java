package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Base;
import model.Funcionario;
import model.dao.BaseDAO;
import model.dao.FuncionarioDAO;
import util.Alerta;

public class FuncionarioController {

  @FXML private TextField txtNome, txtCpf, txtTelefone, txtEndereco, txtSalario, txtCarga;
  @FXML private PasswordField txtSenha;
  @FXML private ComboBox<String> cbFuncao, cbCategoria;

  // Alterado para tipo Base
  @FXML private ComboBox<Base> cbBase;

  // Campos Motorista
  @FXML private Label lblCnh, lblCat;
  @FXML private TextField txtCnh;

  @FXML
  public void initialize() {
    // CARREGA BASES DO BANCO
    cbBase.setItems(FXCollections.observableArrayList(new BaseDAO().listar()));

    cbFuncao.setItems(FXCollections.observableArrayList("Analista", "Motorista", "Admin"));
    cbCategoria.setItems(FXCollections.observableArrayList("A", "B", "C", "D", "E", "AB", "AD", "AE"));

    cbFuncao.valueProperty().addListener((obs, antigo, novo) -> {
      boolean ehMotorista = "Motorista".equals(novo);
      lblCnh.setVisible(ehMotorista); txtCnh.setVisible(ehMotorista);
      lblCnh.setManaged(ehMotorista); txtCnh.setManaged(ehMotorista);

      lblCat.setVisible(ehMotorista); cbCategoria.setVisible(ehMotorista);
      lblCat.setManaged(ehMotorista); cbCategoria.setManaged(ehMotorista);
    });
  }

  @FXML
  public void acaoSalvar() {
    try {
      if (cbBase.getValue() == null) {
          Alerta.mostrarErro("Erro", "Selecione a Base de Lotação!");
          return;
      }

      String nome = txtNome.getText();
      String cpf = txtCpf.getText();
      double salario = 0.0;
      if (!txtSalario.getText().isEmpty()) {
          salario = Double.parseDouble(txtSalario.getText().replace(",", "."));
      }

      // Pega o ID real da base selecionada
      int idBase = cbBase.getValue().getId();

      Funcionario f = new Funcionario(nome, cpf, txtSenha.getText(), cbFuncao.getValue(), idBase,
                                      salario, txtCarga.getText(), txtTelefone.getText(), txtEndereco.getText(),
                                      txtCnh.getText(), cbCategoria.getValue());

      FuncionarioDAO dao = new FuncionarioDAO();
      if (dao.salvar(f)) {
        Alerta.mostrarSucesso("Sucesso", "Funcionário cadastrado!");
        limparCampos();
      }
    } catch (NumberFormatException e) {
      Alerta.mostrarErro("Erro", "Salário inválido.");
    }
  }

  private void limparCampos() {
      txtNome.clear(); txtCpf.clear(); txtSenha.clear();
      txtSalario.clear(); txtCarga.clear(); txtTelefone.clear(); txtEndereco.clear();
      cbBase.getSelectionModel().clearSelection();
  }
}
