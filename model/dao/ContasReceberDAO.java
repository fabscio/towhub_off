package model.dao;

import database.ConexaoFactory;
import model.LoteCobranca;
import java.sql.*;

public class ContasReceberDAO {
  public boolean salvar(LoteCobranca lote) {
    Connection conn = null;
    String sqlLote = "INSERT INTO lotes_cobranca (id_cliente, data_emissao, data_vencimento, status, valor_total) VALUES (?, ?, ?, ?, ?) RETURNING id";
    String sqlUpdate = "UPDATE ordem_servico SET id_lote = ? WHERE id = ?";

    try {
      conn = ConexaoFactory.getConexao();
      conn.setAutoCommit(false);

      PreparedStatement stmt = conn.prepareStatement(sqlLote);
      stmt.setInt(1, lote.getIdCliente());
      stmt.setDate(2, Date.valueOf(lote.getEmissao()));
      stmt.setDate(3, Date.valueOf(lote.getVencimento()));
      stmt.setString(4, lote.getStatus());
      stmt.setDouble(5, lote.getValorTotal());

      ResultSet rs = stmt.executeQuery();
      int idLote = 0;
      if (rs.next()) idLote = rs.getInt(1);

      PreparedStatement stmtUp = conn.prepareStatement(sqlUpdate);
      for (Integer idOs : lote.getIdsOs()) {
        stmtUp.setInt(1, idLote);
        stmtUp.setInt(2, idOs);
        stmtUp.executeUpdate();
      }
      conn.commit();
      return true;
    } catch (SQLException e) {
      try { if(conn != null) conn.rollback(); } catch(Exception ex) {}
      e.printStackTrace();
      return false;
    }
  }
}
