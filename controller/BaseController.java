package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Base;
import model.dao.BaseDAO;

public class BaseController {
  @FXML private TextField txtNome, txtCnpj, txtEndereco;

  @FXML
  public void acaoSalvar() {
    Base b = new Base(txtCnpj.getText(), txtNome.getText(), txtEndereco.getText());

    if (new BaseDAO().salvar(b)) {
      System.out.println("Base salva com sucesso!");
      txtNome.clear(); txtCnpj.clear(); txtEndereco.clear();
    }
  }
}
