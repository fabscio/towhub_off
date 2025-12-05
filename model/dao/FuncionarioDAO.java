package model.dao;

import database.ConexaoFactory;
import model.Funcionario;
import java.sql.*;

public class FuncionarioDAO {

    // 1. Método para LOGIN
    public boolean autenticar(String cpf, String senha) {
        String sql = "SELECT * FROM funcionarios WHERE cpf = ? AND senha = ?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Retorna true se achou alguém
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Método para CADASTRO (Faltava este!)
    public boolean salvar(Funcionario f) {
        String sql = "INSERT INTO funcionarios (nome, cpf, senha, funcao, id_base, salario_base, " +
                     "carga_horaria, telefone, endereco, cnh, categoria_cnh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCpf());
            stmt.setString(3, f.getSenha());
            stmt.setString(4, f.getFuncao());
            stmt.setInt(5, f.getIdBase());
            stmt.setDouble(6, f.getSalarioBase());
            stmt.setString(7, f.getCargaHoraria());
            stmt.setString(8, f.getTelefone());
            stmt.setString(9, f.getEndereco());
            stmt.setString(10, f.getCnh());
            stmt.setString(11, f.getCategoriaCnh());

            stmt.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao salvar funcionário: " + e.getMessage());
            return false;
        }
    }
}
