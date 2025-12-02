package model.dao;
import database.ConexaoFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {
  // Retorna uma lista de Arrays de String para facilitar a exibição na tabela genérica
  public List<String[]> buscarOS(String base, String cliente) {
    List<String[]> lista = new ArrayList<>();
    String sql = "SELECT * FROM relatorio_os WHERE 1=1";
    if (base != null) sql += " AND base = '" + base + "'";
    // Adicione mais filtros se precisar

    try (Connection conn = ConexaoFactory.getConexao();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

      while(rs.next()) {
        lista.add(new String[]{
          String.valueOf(rs.getInt("id")),
          String.valueOf(rs.getDate("data")),
          rs.getString("cliente"),
          rs.getString("motorista"),
          rs.getString("veiculo"),
          String.valueOf(rs.getDouble("valor_total"))
        });
      }
    } catch (Exception e) { e.printStackTrace(); }
    return lista;
  }
}
