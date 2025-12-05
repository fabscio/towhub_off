package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.dao.FuncionarioDAO;
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

        // Se o banco não estiver acessível, o DAO trata ou retorna false
        if (dao.autenticar(cpf, senha)) {
            abrirMenu();
        } else {
            // Se lblMensagem for nulo (não estiver no FXML), evitamos o erro
            if (lblMensagem != null) {
                lblMensagem.setText("Acesso negado. Verifique CPF/Senha.");
                lblMensagem.setStyle("-fx-text-fill: #c0392b;");
            } else {
                System.out.println("Acesso negado. (Label de mensagem não encontrado na tela)");
            }
        }
    }

    private void abrirMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu.fxml"));
            Parent root = loader.load();

            // Pega a janela atual através do botão
            Stage palcoAtual = (Stage) btnEntrar.getScene().getWindow();
            palcoAtual.close();

            // Abre o Menu
            Stage palcoMenu = new Stage();
            palcoMenu.setScene(new Scene(root));
            palcoMenu.setTitle("Sistema Tow Hub");
            palcoMenu.setResizable(false);
            palcoMenu.show();
            palcoMenu.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar o menu: " + e.getMessage());
        }
    }
}
