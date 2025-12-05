package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Base;
import model.dao.BaseDAO;
import util.Alerta;

public class BaseController {
  @FXML private TextField txtNome, txtCnpj, txtEndereco;

  @FXML
  public void acaoSalvar() {
    if (txtNome.getText().isEmpty() || txtCnpj.getText().isEmpty()) {
        Alerta.mostrarErro("Erro", "Nome e CNPJ são obrigatórios.");
        return;
    }

    Base b = new Base(txtCnpj.getText(), txtNome.getText(), txtEndereco.getText());

    if (new BaseDAO().salvar(b)) {
      Alerta.mostrarSucesso("Sucesso", "Base cadastrada com sucesso!");
      limparCampos();
    } else {
      Alerta.mostrarErro("Erro", "Falha ao salvar Base (verifique se o CNPJ já existe).");
    }
  }

  private void limparCampos() {
      txtNome.clear();
      txtCnpj.clear();
      txtEndereco.clear();
  }
}
