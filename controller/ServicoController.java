package controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Servico;
import model.dao.ServicoDAO;

public class ServicoController {
  @FXML private TextField txtNome, txtValor;

  @FXML
  public void acaoSalvar() {
    try {
      String nome = txtNome.getText();
      double valor = Double.parseDouble(txtValor.getText().replace(",", "."));

      Servico s = new Servico(nome, valor);
      if (new ServicoDAO().salvar(s)) {
        System.out.println("Serviço salvo!");
        txtNome.clear(); txtValor.clear();
      }
    } catch (Exception e) { System.out.println("Valor inválido"); }
  }
}
