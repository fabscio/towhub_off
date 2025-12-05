import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
import java.io.IOException;

// Importa a conexão para testar se o banco está acessível ao abrir
import database.ConexaoFactory;
import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // 1. Teste rápido de conexão (Opcional, mas bom para debug)
            testarConexaoBanco();

            // 2. Carrega o arquivo FXML de Login
            // O caminho deve começar com / para indicar a raiz do classpath
            URL arquivoFXML = getClass().getResource("/view/login.fxml");

            if (arquivoFXML == null) {
                System.out.println("ERRO CRÍTICO: Arquivo /view/login.fxml não encontrado!");
                System.exit(1);
            }

            FXMLLoader loader = new FXMLLoader(arquivoFXML);
            Parent root = loader.load();

            // 3. Configura a Cena
            // O tamanho da tela de login é definido no próprio FXML (400x550),
            // então new Scene(root) respeita esse tamanho.
            Scene scene = new Scene(root);

            // 4. Configura o Palco (Janela)
            //primaryStage.setTitle("Sistema Tow Hub - Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // Login geralmente é fixo
            primaryStage.centerOnScreen();

            // 5. Mostra a Janela
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a interface gráfica: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro fatal ao iniciar a aplicação: " + e.getMessage());
        }
    }

    private void testarConexaoBanco() {
        System.out.println("Iniciando Sistema Tow Hub...");
        try (Connection conn = ConexaoFactory.getConexao()) {
            System.out.println("Banco de Dados: CONECTADO COM SUCESSO!");
        } catch (Exception e) {
            System.err.println("AVISO: Não foi possível conectar ao Banco de Dados.");
            System.err.println("Erro: " + e.getMessage());
            // Não fechamos o app aqui para permitir que o dev veja o erro no console,
            // mas na prática o login vai falhar depois.
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
