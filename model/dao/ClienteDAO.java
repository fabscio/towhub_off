package model.dao;

import database.ConexaoFactory;
import model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO {

  /**
   * Salva um novo cliente no banco de dados.
   * @param cliente O objeto com os dados preenchidos
   * @return true se salvou com sucesso, false se deu erro
   */
  public boolean salvar(Cliente cliente) {
    
    String sql = "INSERT INTO clientes (tipo, nome, cpf_cnpj, telefone, email, endereco, id_base) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conexao = ConexaoFactory.getConexao();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

      // Preenche os valores do SQL
      stmt.setString(1, cliente.getTipo());
      stmt.setString(2, cliente.getNome());
      stmt.setString(3, cliente.getCpfOuCnpj());
      stmt.setString(4, cliente.getTelefone());
      stmt.setString(5, cliente.getEmail());
      stmt.setString(6, cliente.getEndereco());
      stmt.setInt(7, cliente.getIdBase());

      // Executa o comando
      stmt.execute();
      
      return true;

    } catch (SQLException e) {
      System.out.println("Erro ao salvar cliente no banco: " + e.getMessage());
      return false;
    }
  }
}
