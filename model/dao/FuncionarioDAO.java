package model.dao;

import database.ConexaoFactory;
import model.Funcionario;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class FuncionarioDAO {

    public boolean autenticar(String cpf, String senha) {
        String sql = "SELECT * FROM funcionarios WHERE cpf = ? AND senha = ?";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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

    // NOVO: Lista filtrada por função (Ex: trazer só Motoristas)
    public List<Funcionario> listarPorFuncao(String funcao) {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionarios WHERE funcao = ? ORDER BY nome";

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcao);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setCpf(rs.getString("cpf"));
                f.setFuncao(rs.getString("funcao"));
                // ... outros campos se necessário
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
