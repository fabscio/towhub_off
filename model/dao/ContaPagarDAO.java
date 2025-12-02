package model.dao;

import database.ConexaoFactory;
import model.ContaPagar;
import model.ParcelaPagar;
import java.sql.*;

public class ContaPagarDAO {

  public boolean salvar(ContaPagar conta) {
    Connection conn = null;
    PreparedStatement stmtConta = null;
    PreparedStatement stmtParcela = null;

    String sqlConta = "INSERT INTO contas_pagar (id_fornecedor, id_base, data_compra, valor_total) VALUES (?, ?, ?, ?) RETURNING id";
    String sqlParcela = "INSERT INTO parcelas_pagar (id_conta, numero_parcela, data_vencimento, valor) VALUES (?, ?, ?, ?)";

    try {
      conn = ConexaoFactory.getConexao();
      conn.setAutoCommit(false); // Inicia Transação

      // 1. Salva a Conta
      stmtConta = conn.prepareStatement(sqlConta);
      stmtConta.setInt(1, conta.getIdFornecedor());
      stmtConta.setInt(2, conta.getIdBase());
      stmtConta.setDate(3, Date.valueOf(conta.getDataCompra()));
      stmtConta.setDouble(4, conta.getValorTotal());
      
      ResultSet rs = stmtConta.executeQuery();
      int idGerado = 0;
      if (rs.next()) idGerado = rs.getInt("id");

      // 2. Salva as Parcelas
      stmtParcela = conn.prepareStatement(sqlParcela);
      for (ParcelaPagar p : conta.getParcelas()) {
        stmtParcela.setInt(1, idGerado);
        stmtParcela.setInt(2, p.getNumeroParcela());
        stmtParcela.setDate(3, Date.valueOf(p.getVencimento()));
        stmtParcela.setDouble(4, p.getValor());
        stmtParcela.executeUpdate();
      }

      conn.commit(); // Confirma
      return true;

    } catch (SQLException e) {
      try { if(conn != null) conn.rollback(); } catch(SQLException ex) {}
      System.out.println("Erro ao salvar conta: " + e.getMessage());
      return false;
    } finally {
      // Feche as conexões aqui (omitido para brevidade)
    }
  }
}
