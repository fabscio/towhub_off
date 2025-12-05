package model.dao;

import database.ConexaoFactory;
import model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public boolean salvar(Cliente cliente) {
        // Adicionado nome_gestor na query
        String sql = "INSERT INTO clientes (tipo, nome, cpf_cnpj, nome_gestor, telefone, email, endereco, id_base) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = ConexaoFactory.getConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cliente.getTipo());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getCpfOuCnpj());
            stmt.setString(4, cliente.getNomeGestor()); // Salva o gestor
            stmt.setString(5, cliente.getTelefone());
            stmt.setString(6, cliente.getEmail());
            stmt.setString(7, cliente.getEndereco());
            stmt.setInt(8, cliente.getIdBase());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao salvar cliente: " + e.getMessage());
            return false;
        }
    }

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nome";

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTipo(rs.getString("tipo"));
                c.setCpfOuCnpj(rs.getString("cpf_cnpj"));
                c.setNomeGestor(rs.getString("nome_gestor")); // Lê o gestor
                c.setTelefone(rs.getString("telefone"));
                c.setEmail(rs.getString("email"));
                c.setEndereco(rs.getString("endereco"));
                c.setIdBase(rs.getInt("id_base"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
