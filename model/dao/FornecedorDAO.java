package model.dao;

import database.ConexaoFactory;
import model.Fornecedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FornecedorDAO {

  public boolean salvar(Fornecedor f) {
    String sql = "INSERT INTO fornecedores (tipo, nome, cpf_cnpj, nicho, telefone, email, endereco) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";

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
}
