package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ClientController {

    // --- Componentes de Tipo (PF/PJ) ---
    @FXML private RadioButton rbPF;
    @FXML private RadioButton rbPJ;
    @FXML private ToggleGroup tipoCliente;

    // --- Campos Comuns ---
    @FXML private TextField txtNome;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtEndereco;

    // NOVO: Base agora é um ComboBox
    @FXML private ComboBox<String> cbBase;

    // --- Campos Específicos PF ---
    @FXML private Label lblCpf;
    @FXML private TextField txtCpf;

    // --- Campos Específicos PJ ---
    @FXML private Label lblCnpj;
    @FXML private TextField txtCnpj;

    @FXML private Label lblGestor;
    @FXML private TextField txtGestor;

    @FXML
    public void initialize() {
        // 1. Carregar Bases (Dados fictícios por enquanto)
        cbBase.setItems(FXCollections.observableArrayList("Matriz", "Filial Norte", "Filial Sul"));

        // 2. Configurar Listener para troca de PF/PJ
        tipoCliente.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            boolean isPJ = rbPJ.isSelected();
            atualizarVisibilidade(isPJ);
        });

        // Garante o estado inicial correto
        atualizarVisibilidade(rbPJ.isSelected());
    }

    private void atualizarVisibilidade(boolean isPJ) {
        // Se for PJ, esconde CPF e mostra CNPJ/Gestor
        // setManaged(true) faz o campo ocupar espaço no layout
        // setManaged(false) faz o campo sumir e liberar espaço

        // Configuração PF
        lblCpf.setVisible(!isPJ);   lblCpf.setManaged(!isPJ);
        txtCpf.setVisible(!isPJ);   txtCpf.setManaged(!isPJ);

        // Configuração PJ
        lblCnpj.setVisible(isPJ);   lblCnpj.setManaged(isPJ);
        txtCnpj.setVisible(isPJ);   txtCnpj.setManaged(isPJ);

        lblGestor.setVisible(isPJ); lblGestor.setManaged(isPJ);
        txtGestor.setVisible(isPJ); txtGestor.setManaged(isPJ);
    }

    @FXML
    public void handleSalvar() {
        System.out.println("Salvar Cliente clicado");
        System.out.println("Base Selecionada: " + cbBase.getValue());

        if (rbPF.isSelected()) {
            System.out.println("Tipo: PF, CPF: " + txtCpf.getText());
        } else {
            System.out.println("Tipo: PJ, CNPJ: " + txtCnpj.getText());
        }

        // Aqui entrará a lógica de INSERT no Banco de Dados
    }
}
