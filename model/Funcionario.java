package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Funcionario;
import model.dao.FuncionarioDAO;

public class FuncionarioController {

  @FXML private TextField txtNome, txtCpf, txtTelefone, txtEndereco, txtSalario, txtCarga;
  @FXML private PasswordField txtSenha;
  @FXML private ComboBox<String> cbFuncao, cbBase, cbCategoria;

  // Campos Motorista
  @FXML private Label lblCnh, lblCat;
  @FXML private TextField txtCnh;

  @FXML
  public void initialize() {
    cbBase.setItems(FXCollections.observableArrayList("Matriz", "Filial Norte"));
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
      String nome = txtNome.getText();
      String cpf = txtCpf.getText();
      double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));
      int idBase = 1; // Simulação: Pegar do combo futuramente

      Funcionario f = new Funcionario(nome, cpf, txtSenha.getText(), cbFuncao.getValue(), idBase,
                                      salario, txtCarga.getText(), txtTelefone.getText(), txtEndereco.getText(),
                                      txtCnh.getText(), cbCategoria.getValue());

      FuncionarioDAO dao = new FuncionarioDAO();
      if (dao.salvar(f)) {
        System.out.println("Funcionário cadastrado!");
      }
    } catch (NumberFormatException e) {
      System.out.println("Erro: Salário inválido.");
    }
  }
}
