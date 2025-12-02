package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EmployeeController {
    @FXML private ComboBox<String> cbFuncao;
    @FXML private ComboBox<String> cbCategoria;
    @FXML private Label lblCnh, lblCat;
    @FXML private TextField txtCnh;

    @FXML
    public void initialize() {
        cbFuncao.setItems(FXCollections.observableArrayList("Analista", "Motorista"));
        cbCategoria.setItems(FXCollections.observableArrayList("A", "AB", "B", "BC", "BD", "BE", "AC", "AD", "AE"));

        // Listener para mostrar campos se for Motorista
        cbFuncao.valueProperty().addListener((obs, oldVal, newVal) -> {
            boolean isMotorista = "Motorista".equals(newVal);
            
            lblCnh.setVisible(isMotorista); txtCnh.setVisible(isMotorista);
            lblCnh.setManaged(isMotorista); txtCnh.setManaged(isMotorista);

            lblCat.setVisible(isMotorista); cbCategoria.setVisible(isMotorista);
            lblCat.setManaged(isMotorista); cbCategoria.setManaged(isMotorista);
        });
    }

    @FXML public void handleSalvar() { System.out.println("Salvar Funcionário"); }
}
