import database.ConexaoFactory;
import java.sql.Connection;

public class TesteBanco {
    public static void main(String[] args) {
        System.out.println("Tentando conectar...");
        
        try (Connection conn = ConexaoFactory.getConexao()) {
            System.out.println("CONEXÃO BEM SUCEDIDA!");
            System.out.println("Conectado ao: " + conn.getMetaData().getURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
