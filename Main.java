import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

import controller.LoginController;
import controller.MenuController;
import controller.CreateOrderController;
import controller.ClientController;
import controller.EmployeeController;
import controller.ServiceController;
import controller.SupplierController;
import controller.PayableController;
import controller.ReceivableController;
import controller.ReportOSController;
import controller.ReportPaymentsController;
import controller.ReportReceiptsController;
import controller.BaseController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            URL fxmlLocation = getClass().getResource("/view/login.fxml");

            if (fxmlLocation == null) {
                System.out.println("ERRO CRÍTICO: Não foi possível encontrar /view/login.fxml");
                System.exit(1);
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            // Size for LOGIN screen (Matches FXML preference)
            Scene scene = new Scene(root, 1280, 720);

            primaryStage.setTitle("Tow Hub System - Login");
            primaryStage.setScene(scene);

            // Login window is fixed size
            primaryStage.setResizable(false);

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao iniciar a aplicação: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
