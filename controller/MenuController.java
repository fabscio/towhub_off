package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class MenuController {

    @FXML private BorderPane layoutPrincipal;

    // --- Método Genérico para Trocar de Tela ---
    private void carregarTela(String caminhoFxml) {
        try {
            URL url = getClass().getResource(caminhoFxml);
            if (url == null) {
                System.out.println("ERRO: Arquivo não encontrado: " + caminhoFxml);
                return;
            }
            Parent novaTela = FXMLLoader.load(url);
            layoutPrincipal.setCenter(novaTela); // Troca apenas o miolo
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar tela: " + e.getMessage());
        }
    }

    // --- OPERACIONAL ---
    @FXML public void abrirCriarOrdem(ActionEvent e) { carregarTela("/view/criarOrdemServico.fxml"); }
    @FXML public void abrirEditarOrdem(ActionEvent e) { carregarTela("/view/editarOrdemServico.fxml"); }

    // --- FINANCEIRO ---
    @FXML public void abrirContasPagar(ActionEvent e) { carregarTela("/view/contasPagar.fxml"); }
    @FXML public void abrirContasReceber(ActionEvent e) { carregarTela("/view/contasReceber.fxml"); }

    // --- CADASTROS ---
    @FXML public void abrirBase(ActionEvent e) { carregarTela("/view/cadastroBase.fxml"); }
    @FXML public void abrirCliente(ActionEvent e) { carregarTela("/view/cadastroCliente.fxml"); }
    @FXML public void abrirFornecedor(ActionEvent e) { carregarTela("/view/cadastroFornecedor.fxml"); }
    @FXML public void abrirFuncionario(ActionEvent e) { carregarTela("/view/cadastroFuncionario.fxml"); }
    @FXML public void abrirServico(ActionEvent e) { carregarTela("/view/cadastroServico.fxml"); }

    // --- RELATÓRIOS ---
    @FXML public void abrirRelatorioOS(ActionEvent e) { carregarTela("/view/relatorioOS.fxml"); }
    @FXML public void abrirRelatorioRecebimentos(ActionEvent e) { carregarTela("/view/relatorioRecebimentos.fxml"); }
    @FXML public void abrirRelatorioPagamentos(ActionEvent e) { carregarTela("/view/relatorioPagamentos.fxml"); }

    // --- SAIR ---
    @FXML
    public void acaoLogout(ActionEvent event) {
        try {
            // Volta para o Login
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage palcoAtual = (Stage) layoutPrincipal.getScene().getWindow();
            palcoAtual.close();

            Stage palcoLogin = new Stage();
            palcoLogin.setScene(new Scene(root));
            palcoLogin.setTitle("Tow Hub - Login");
            palcoLogin.setResizable(false);
            palcoLogin.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
