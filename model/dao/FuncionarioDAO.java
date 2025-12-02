package model.dao;

import database.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FuncionarioDAO {

    // Método para verificar se o usuário existe
    public boolean autenticar(String cpf, String senha) {
        String sql = "SELECT * FROM funcionarios WHERE cpf = ? AND senha = ?";
        
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            
            try (ResultSet rs = stmt.executeQuery()) {
                // Se retornar alguma linha (rs.next), o login é válido
                return rs.next();
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao autenticar: " + e.getMessage());
            return false;
        }
    }
}
