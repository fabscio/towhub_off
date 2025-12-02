package model.dao;
import database.ConexaoFactory;
import model.LoteCobranca;
import java.sql.*;

public class ContasReceberDAO {

  public boolean salvar(LoteCobranca lote) {
    Connection conn = null;
    PreparedStatement stmtLote = null;
    PreparedStatement stmtUpdateOS = null;

    String sqlLote = "INSERT INTO lotes_cobranca (id_cliente, data_emissao, data_vencimento, status, valor_total) VALUES (?, ?, ?, ?, ?) RETURNING id";
    
    // Atualiza a OS para dizer que ela agora pertence a este lote
    String sqlUpdateOS = "UPDATE ordem_servico SET id_lote = ? WHERE id = ?";

    try {
      conn = ConexaoFactory.getConexao();
      conn.setAutoCommit(false);

      // 1. Cria o Lote
      stmtLote = conn.prepareStatement(sqlLote);
      stmtLote.setInt(1, lote.getIdCliente());
      stmtLote.setDate(2, Date.valueOf(lote.getEmissao()));
      stmtLote.setDate(3, Date.valueOf(lote.getVencimento()));
      stmtLote.setString(4, lote.getStatus());
      stmtLote.setDouble(5, lote.getValorTotal());

      ResultSet rs = stmtLote.executeQuery();
      int idLote = 0;
      if (rs.next()) idLote = rs.getInt("id");

      // 2. Vincula as OSs ao Lote
      stmtUpdateOS = conn.prepareStatement(sqlUpdateOS);
      for (Integer idOs : lote.getIdsOrdensServico()) {
        stmtUpdateOS.setInt(1, idLote);
        stmtUpdateOS.setInt(2, idOs);
        stmtUpdateOS.executeUpdate();
      }

      conn.commit();
      return true;

    } catch (SQLException e) {
      try { if(conn != null) conn.rollback(); } catch(SQLException ex) {}
      e.printStackTrace();
      return false;
    } finally {
       // Fechar conexões
    }
  }
  
  // Método auxiliar para buscar OS pendentes no Controller
  // public List<OrdemServico> buscarOsPendentes(int idCliente) { ... }
}
