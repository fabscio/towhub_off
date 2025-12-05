package model.dao;

import database.ConexaoFactory;
import model.Fornecedor;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class FornecedorDAO {

    public boolean salvar(Fornecedor f) {
        String sql = "INSERT INTO fornecedores (tipo, nome, cpf_cnpj, nicho, telefone, email, endereco) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, f.getTipo());
            stmt.setString(2, f.getNome());
            stmt.setString(3, f.getCpfOuCnpj());
            stmt.setString(4, f.getNicho());
            stmt.setString(5, f.getTelefone());
            stmt.setString(6, f.getEmail());
            stmt.setString(7, f.getEndereco());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar fornecedor: " + e.getMessage());
            return false;
        }
    }

    // NOVO MÉTODO
    public List<Fornecedor> listar() {
        List<Fornecedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM fornecedores ORDER BY nome";

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Fornecedor f = new Fornecedor();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setCpfOuCnpj(rs.getString("cpf_cnpj"));
                // ... setar outros campos se necessário
                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
