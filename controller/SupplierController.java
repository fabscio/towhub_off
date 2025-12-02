package controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SupplierController {
    @FXML private RadioButton rbPF, rbPJ;
    @FXML private Label lblCpf, lblCnpj, lblIM, lblIE;
    @FXML private TextField txtCpf, txtCnpj, txtIM, txtIE;

    @FXML
    public void initialize() {
        rbPF.getToggleGroup().selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            boolean isPJ = rbPJ.isSelected();
            
            lblCpf.setVisible(!isPJ); txtCpf.setVisible(!isPJ);
            lblCpf.setManaged(!isPJ); txtCpf.setManaged(!isPJ);

            lblCnpj.setVisible(isPJ); txtCnpj.setVisible(isPJ);
            lblCnpj.setManaged(isPJ); txtCnpj.setManaged(isPJ);

            lblIM.setVisible(isPJ); txtIM.setVisible(isPJ);
            lblIM.setManaged(isPJ); txtIM.setManaged(isPJ);

            lblIE.setVisible(isPJ); txtIE.setVisible(isPJ);
            lblIE.setManaged(isPJ); txtIE.setManaged(isPJ);
        });
    }

    @FXML public void handleSalvar() { System.out.println("Salvar Fornecedor"); }
}
