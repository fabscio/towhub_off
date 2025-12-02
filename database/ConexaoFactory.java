package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    // 1. Dados de conexão (MUDE A SENHA AQUI)
    private static final String URL = "jdbc:postgresql://localhost:5432/bd_towhub";
    private static final String USER = "postgres";
    private static final String PASS = "123456"; // <--- COLOQUE SUA SENHA DO POSTGRES AQUI

    // 2. Método para obter a conexão
    public static Connection getConexao() {
        try {
            // Força o carregamento do driver (necessário em alguns ambientes manuais)
            Class.forName("org.postgresql.Driver");
            
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Erro: Driver do PostgreSQL não encontrado na biblioteca!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no banco de dados: " + e.getMessage(), e);
        }
    }
}
