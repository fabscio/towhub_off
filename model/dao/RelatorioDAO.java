package model.dao;
import database.ConexaoFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {
  
  // Método genérico pode ser difícil, melhor fazer específico
  public List<String[]> buscarRelatorioOS(String base, String cliente) {
    List<String[]> resultados = new ArrayList<>();
    
    // Usa a VIEW que criamos no SQL
    String sql = "SELECT * FROM relatorio_os WHERE 1=1"; 
    
    if (base != null && !base.isEmpty()) sql += " AND base = '" + base + "'";
    if (cliente != null && !cliente.isEmpty()) sql += " AND cliente LIKE '%" + cliente + "%'";
    
    try (Connection conn = ConexaoFactory.getConexao();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
         
       while(rs.next()) {
         // Guarda os dados num array de strings simples para exibir na tabela
         resultados.add(new String[]{
           String.valueOf(rs.getInt("id")),
           rs.getString("cliente"),
           String.valueOf(rs.getDouble("valor_total"))
           // ... outros campos
         });
       }
    } catch (Exception e) { e.printStackTrace(); }
    return resultados;
  }
}
