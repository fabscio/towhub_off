package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane; // IMPORTANTE
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL; // IMPORTANTE

public class MenuController {

    // VINCULA COM O FXML (O BorderPane principal deve ter fx:id="mainLayout")
    @FXML
    private BorderPane mainLayout;

    // --- LÓGICA DE NAVEGAÇÃO ---
    private void loadScreen(String fxmlPath) {
        try {
            URL fxmlUrl = getClass().getResource(fxmlPath);
            if (fxmlUrl == null) {
                System.out.println("ERRO: Arquivo FXML não encontrado: " + fxmlPath);
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent view = loader.load();

            // Troca apenas o CENTRO do BorderPane, mantendo o menu lateral
            mainLayout.setCenter(view);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela: " + e.getMessage());
        }
    }

    // --- OPERACIONAL ---
    @FXML
    public void showCreateOrder(ActionEvent event) {
        // Agora isso vai funcionar porque o método loadScreen existe
        loadScreen("/view/createOrder.fxml");
    }

    @FXML
    public void showEditOrder(ActionEvent event) {
        System.out.println("Editar Ordem de Serviço clicado");
    }

    // --- FINANCEIRO ---
    @FXML
    public void showPayable(ActionEvent event) {
        loadScreen("/view/createPayable.fxml");
    }

    @FXML
    public void showReceivable(ActionEvent event) {
        loadScreen("/view/createReceivable.fxml");
    }

    // --- CADASTROS ---
    @FXML
    public void showClient(ActionEvent event) {
        loadScreen("/view/createClient.fxml");
    }

    @FXML
    public void showSupplier(ActionEvent event) {
        loadScreen("/view/createSupplier.fxml");
    }

    @FXML
    public void showEmployee(ActionEvent event) {
        loadScreen("/view/createEmployee.fxml");
    }

    @FXML
    public void showService(ActionEvent event) {
        loadScreen("/view/createService.fxml");
    }

    @FXML
    public void showBase(ActionEvent event) {
        loadScreen("/view/createBase.fxml");
    }

    // --- RELATÓRIOS ---
    @FXML
    public void showServiceOrdersReport(ActionEvent event) {
        loadScreen("/view/reportServiceOrders.fxml");
    }

    @FXML
    public void showReceiptsReport(ActionEvent event) {
        loadScreen("/view/reportReceipts.fxml");
    }

    @FXML
    public void showPaymentsReport(ActionEvent event) {
        loadScreen("/view/reportPayments.fxml");
    }

    // --- SAIR ---
    @FXML
    public void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            Parent root = loader.load();

            // 1. Pega o botão que acionou o evento para descobrir a janela atual
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            // 2. Abre a tela de Login novamente
            Stage loginStage = new Stage();
            // Tamanho do login (400x550 conforme o FXML do login)
            Scene scene = new Scene(root, 400, 550);

            loginStage.setTitle("Tow Hub System - Login");
            loginStage.setScene(scene);
            loginStage.setResizable(false);
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERRO AO TROCAR PARA A TELA DE LOGIN: " + e.getMessage());
        }
    }
}
