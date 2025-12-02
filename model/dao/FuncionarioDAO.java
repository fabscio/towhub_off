package model.dao;

import database.ConexaoFactory;
import java.sql.*;

public class FuncionarioDAO {

    public boolean autenticar(String cpf, String senha) {
        System.out.println("--- INICIANDO AUTENTICAÇÃO ---");
        System.out.println("Tentando conectar ao banco...");

        String sql = "SELECT * FROM funcionarios WHERE cpf = ? AND senha = ?";

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Conexão OK! Buscando usuário...");
            System.out.println("CPF procurado: [" + cpf + "]");
            System.out.println("Senha procurada: [" + senha + "]");

            stmt.setString(1, cpf);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("SUCESSO: Usuário encontrado: " + rs.getString("nome"));
                return true;
            } else {
                System.out.println("FALHA: Nenhum usuário encontrado com esses dados.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("ERRO CRÍTICO NO BANCO: " + e.getMessage());
            e.printStackTrace(); // Mostra o erro completo no terminal
            return false;
        }
    }

    // Mantenha o método salvar aqui se você já tinha colocado...
}
