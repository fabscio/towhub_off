package controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServiceController {
    @FXML private TextField txtNome;
    @FXML private TextField txtValor;

    @FXML public void handleSalvar() { System.out.println("Salvar Serviço"); }
}
