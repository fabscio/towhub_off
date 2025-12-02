package model.dao;

import database.ConexaoFactory;
import model.ContaPagar;
import model.ParcelaPagar;
import java.sql.*;

public class ContaPagarDAO {
  public boolean salvar(ContaPagar conta) {
    Connection conn = null;
    String sqlC = "INSERT INTO contas_pagar (id_fornecedor, id_base, data_compra, valor_total) VALUES (?, ?, ?, ?) RETURNING id";
    String sqlP = "INSERT INTO parcelas_pagar (id_conta, numero_parcela, data_vencimento, valor) VALUES (?, ?, ?, ?)";

    try {
      conn = ConexaoFactory.getConexao();
      conn.setAutoCommit(false);

      PreparedStatement stmtC = conn.prepareStatement(sqlC);
      stmtC.setInt(1, conta.getIdFornecedor());
      stmtC.setInt(2, conta.getIdBase());
      stmtC.setDate(3, Date.valueOf(conta.getDataCompra()));
      stmtC.setDouble(4, conta.getValorTotal());

      ResultSet rs = stmtC.executeQuery();
      int idConta = 0;
      if (rs.next()) idConta = rs.getInt(1);

      PreparedStatement stmtP = conn.prepareStatement(sqlP);
      for (ParcelaPagar p : conta.getParcelas()) {
        stmtP.setInt(1, idConta);
        stmtP.setInt(2, p.getNumero());
        stmtP.setDate(3, Date.valueOf(p.getVencimento()));
        stmtP.setDouble(4, p.getValor());
        stmtP.executeUpdate();
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
