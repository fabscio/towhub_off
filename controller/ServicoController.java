package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Servico;
import model.dao.ServicoDAO;
import util.Alerta;

public class ServicoController {
  @FXML private TextField txtNome, txtValor;

  @FXML
public void acaoSalvar() {
    try {
        String nome = txtNome.getText();
        if (nome.isEmpty()) {
            Alerta.mostrarErro("Erro", "Digite o nome do serviço.");
            return;
        }

        // CORREÇÃO AQUI: Usa a classe Monetario
        double valor = util.Monetario.converterParaDouble(txtValor.getText());

        Servico s = new Servico(nome, valor);
        if (new ServicoDAO().salvar(s)) {
            Alerta.mostrarSucesso("Sucesso", "Serviço registrado!");
            txtNome.clear();
            txtValor.clear();
        } else {
            Alerta.mostrarErro("Erro", "Erro ao salvar no banco.");
        }
    } catch (NumberFormatException e) {
        Alerta.mostrarErro("Erro", "Valor inválido! Use formato: 1200,00");
    }
}
}
