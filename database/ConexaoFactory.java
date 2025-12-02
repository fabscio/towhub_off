package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/bd_towhub";
    private static final String USER = "postgres";
    private static final String PASS = "123456"; // <--- Sua senha definida anteriormente

    public static Connection getConexao() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro na conexão: " + e.getMessage(), e);
        }
    }
}
