package controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BaseController {
    @FXML private TextField txtNome, txtCnpj, txtEndereco;

    @FXML public void handleSalvar() {
        System.out.println("Salvando Base: " + txtNome.getText());
        // Insert into bases...
    }
}
