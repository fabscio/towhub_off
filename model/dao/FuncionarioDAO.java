package model.dao;

import database.ConexaoFactory;
import model.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FuncionarioDAO {

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
      stmt.setString(10, f.getCnh()); // Pode ser null se não for motorista
      stmt.setString(11, f.getCategoriaCnh());

      stmt.execute();
      return true;

    } catch (SQLException e) {
      System.out.println("Erro ao salvar funcionário: " + e.getMessage());
      return false;
    }
  }

  // (Mantenha aqui aquele método 'autenticar' que fizemos antes)
  public boolean autenticar(String cpf, String senha) {
      // ... código de login anterior ...
      return false; // placeholder
  }
}
