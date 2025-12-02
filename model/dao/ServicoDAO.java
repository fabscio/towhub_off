package model.dao;
import database.ConexaoFactory;
import model.Servico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServicoDAO {
  public boolean salvar(Servico s) {
    String sql = "INSERT INTO servicos (nome, valor_padrao) VALUES (?, ?)";
    try (Connection conn = ConexaoFactory.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, s.getNome());
      stmt.setDouble(2, s.getValorPadrao());
      stmt.execute();
      return true;
    } catch (SQLException e) { return false; }
  }
}
