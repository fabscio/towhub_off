package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.dao.FuncionarioDAO;
import util.Alerta; // Opcional aqui, mas bom ter
import java.io.IOException;

public class LoginController {

    @FXML private TextField txtCpf;
    @FXML private PasswordField txtSenha;
    @FXML private Button btnEntrar;
    @FXML private Label lblMensagem;

    @FXML
    public void acaoEntrar(ActionEvent event) {
        String cpf = txtCpf.getText();
        String senha = txtSenha.getText();

        FuncionarioDAO dao = new FuncionarioDAO();

        if (dao.autenticar(cpf, senha)) {
            abrirMenu();
        } else {
            if (lblMensagem != null) {
                lblMensagem.setText("Acesso negado. Verifique CPF/Senha.");
                lblMensagem.setStyle("-fx-text-fill: #c0392b;");
            } else {
                Alerta.mostrarErro("Login", "Acesso Negado.");
            }
        }
    }

    private void abrirMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
            Parent root = loader.load();

            // Fecha login
            Stage palcoAtual = (Stage) btnEntrar.getScene().getWindow();
            palcoAtual.close();

            // Abre Menu
            Stage palcoMenu = new Stage();
            // IMPORTANTE: new Scene(root) usa o tamanho definido no FXML (1100x680)
            palcoMenu.setScene(new Scene(root));
            palcoMenu.setTitle("Sistema Tow Hub");
            // Permitir redimensionar é bom para monitores pequenos
            palcoMenu.setResizable(true);
            palcoMenu.show();
            palcoMenu.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            Alerta.mostrarErro("Erro Crítico", "Erro ao carregar o menu: " + e.getMessage());
        }
    }
}
