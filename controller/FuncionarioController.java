package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Base;
import model.Funcionario;
import model.dao.BaseDAO;
import model.dao.FuncionarioDAO;
import util.Alerta;
import util.Documento; // Import Documento
import util.Monetario; // Import Monetario

public class FuncionarioController {

  @FXML private TextField txtNome, txtCpf, txtTelefone, txtEndereco, txtSalario, txtCarga;
  @FXML private PasswordField txtSenha;
  @FXML private ComboBox<String> cbFuncao, cbCategoria;

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

      // --- CORREÇÃO 1: Limpa CPF ---
      String cpfLimpo = Documento.limpar(txtCpf.getText());

      // --- CORREÇÃO 2: Converte Salário com segurança ---
      double salario = 0.0;
      if (!txtSalario.getText().isEmpty()) {
          salario = Monetario.converterParaDouble(txtSalario.getText());
      }

      int idBase = cbBase.getValue().getId();

      Funcionario f = new Funcionario(nome, cpfLimpo, txtSenha.getText(), cbFuncao.getValue(), idBase,
                                      salario, txtCarga.getText(), txtTelefone.getText(), txtEndereco.getText(),
                                      txtCnh.getText(), cbCategoria.getValue());

      FuncionarioDAO dao = new FuncionarioDAO();
      if (dao.salvar(f)) {
        Alerta.mostrarSucesso("Sucesso", "Funcionário cadastrado!");
        limparCampos();
      }
    } catch (NumberFormatException e) {
      Alerta.mostrarErro("Erro", "Salário inválido. Digite apenas números.");
    } catch (Exception e) {
        e.printStackTrace();
        Alerta.mostrarErro("Erro", "Erro ao salvar: " + e.getMessage());
    }
  }

  private void limparCampos() {
      txtNome.clear(); txtCpf.clear(); txtSenha.clear();
      txtSalario.clear(); txtCarga.clear(); txtTelefone.clear(); txtEndereco.clear();
      cbBase.getSelectionModel().clearSelection();
      // Se quiser limpar campos de motorista também
      txtCnh.clear(); cbCategoria.getSelectionModel().clearSelection();
  }
}
