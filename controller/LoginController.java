package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.dao.FuncionarioDAO; // Importar o DAO
import java.io.IOException;

public class LoginController {

    @FXML private TextField txtCpf;
    @FXML private PasswordField txtSenha; // Certifique-se que no FXML é PasswordField
    @FXML private Label lblNomeUsuario;
    @FXML private Button btnEntrar;

    @FXML
    public void handleEntrar(ActionEvent event) {
        String cpf = txtCpf.getText();
        String senha = txtSenha.getText();

        if (cpf.isEmpty() || senha.isEmpty()) {
            lblNomeUsuario.setText("Preencha CPF e Senha!");
            lblNomeUsuario.setStyle("-fx-text-fill: #c0392b;"); // Vermelho
            return;
        }

        // --- LÓGICA REAL DE BANCO DE DADOS ---
        FuncionarioDAO dao = new FuncionarioDAO();

        if (dao.autenticar(cpf, senha)) {
            // LOGIN SUCESSO
            abrirMenu();
        } else {
            // LOGIN FALHA
            lblNomeUsuario.setText("CPF ou Senha incorretos.");
            lblNomeUsuario.setStyle("-fx-text-fill: #c0392b;");
        }
    }

    private void abrirMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
            Parent root = loader.load();

            // Fechar Login
            Stage loginStage = (Stage) btnEntrar.getScene().getWindow();
            loginStage.close();

            // Abrir Menu em HD
            Stage menuStage = new Stage();
            Scene scene = new Scene(root, 1280, 720); // Resolução HD

            menuStage.setTitle("Sistema de Gestão - Tow Hub");
            menuStage.setScene(scene);
            menuStage.show();
            menuStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            lblNomeUsuario.setText("Erro ao carregar menu: " + e.getMessage());
        }
    }
}
