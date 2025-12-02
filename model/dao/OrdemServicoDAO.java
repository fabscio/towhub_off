// ... (imports existentes)
import model.ItemOrdemServico;
import java.util.ArrayList;
import java.util.List;

public class OrdemServicoDAO {

  // ... (método salvar existente) ...

  /**
   * Busca uma OS completa (Cabeçalho + Itens) pelo ID.
   */
  public OrdemServico buscarPorId(int id) {
    String sqlOs = "SELECT * FROM ordem_servico WHERE id = ?";
    String sqlItens = "SELECT i.*, s.nome FROM itens_os i " +
                      "JOIN servicos s ON i.id_servico = s.id " +
                      "WHERE id_ordem_servico = ?";

    OrdemServico os = null;

    try (Connection conn = ConexaoFactory.getConexao();
         PreparedStatement stmtOs = conn.prepareStatement(sqlOs);
         PreparedStatement stmtItens = conn.prepareStatement(sqlItens)) {

      // 1. Busca Cabeçalho
      stmtOs.setInt(1, id);
      ResultSet rs = stmtOs.executeQuery();

      if (rs.next()) {
        os = new OrdemServico();
        os.setId(rs.getInt("id"));
        os.setDataEmissao(rs.getDate("data_emissao").toLocalDate());
        os.setValorTotal(rs.getDouble("valor_total"));
        os.setVeiculoModelo(rs.getString("veiculo_modelo"));
        os.setVeiculoPlaca(rs.getString("veiculo_placa"));
        os.setContato(rs.getString("contato_segurado"));
        os.setTipoSolicitacao(rs.getString("tipo_solicitacao"));
        os.setEnderecoOrigem(rs.getString("endereco_origem"));
        os.setEnderecoDestino(rs.getString("endereco_destino"));
        os.setPrazo(rs.getInt("prazo"));

        // IDs Estrangeiros (para setar nos Combos depois)
        os.setIdCliente(rs.getInt("id_cliente"));
        os.setIdBase(rs.getInt("id_base"));
        os.setIdMotorista(rs.getInt("id_motorista"));
        os.setIdAnalista(rs.getInt("id_analista"));
      }

      // 2. Se achou a OS, busca os Itens
      if (os != null) {
        stmtItens.setInt(1, id);
        ResultSet rsItens = stmtItens.executeQuery();

        while (rsItens.next()) {
          ItemOrdemServico item = new ItemOrdemServico(
            rsItens.getInt("id_servico"),
            rsItens.getString("nome"), // Nome vem do JOIN
            rsItens.getInt("quantidade"),
            rsItens.getDouble("valor_unitario")
          );
          os.adicionarItem(item);
        }
      }

    } catch (SQLException e) {
      System.out.println("Erro ao buscar OS: " + e.getMessage());
    }
    return os;
  }

  /**
   * Atualiza uma OS existente.
   */
  public boolean atualizar(OrdemServico os) {
    Connection conn = null;
    PreparedStatement stmtOs = null;
    PreparedStatement stmtDel = null;
    PreparedStatement stmtIns = null;

    String sqlOs = "UPDATE ordem_servico SET data_emissao=?, id_cliente=?, id_base=?, " +
                   "veiculo_modelo=?, veiculo_placa=?, contato_segurado=?, valor_total=? WHERE id=?";

    // Estratégia: Remove todos os itens antigos e insere os atuais da tabela
    String sqlDeleteItens = "DELETE FROM itens_os WHERE id_ordem_servico=?";
    String sqlInsertItem = "INSERT INTO itens_os (id_ordem_servico, id_servico, quantidade, valor_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

    try {
      conn = ConexaoFactory.getConexao();
      conn.setAutoCommit(false); // Inicia Transação

      // 1. Atualiza Cabeçalho
      stmtOs = conn.prepareStatement(sqlOs);
      stmtOs.setDate(1, java.sql.Date.valueOf(os.getDataEmissao()));
      stmtOs.setInt(2, os.getIdCliente());
      stmtOs.setInt(3, os.getIdBase());
      stmtOs.setString(4, os.getVeiculoModelo());
      stmtOs.setString(5, os.getVeiculoPlaca());
      stmtOs.setString(6, os.getContato());
      stmtOs.setDouble(7, os.getValorTotal());
      stmtOs.setInt(8, os.getId()); // WHERE ID
      stmtOs.executeUpdate();

      // 2. Limpa itens antigos
      stmtDel = conn.prepareStatement(sqlDeleteItens);
      stmtDel.setInt(1, os.getId());
      stmtDel.executeUpdate();

      // 3. Insere itens novos (ou mantidos)
      stmtIns = conn.prepareStatement(sqlInsertItem);
      for (ItemOrdemServico item : os.getItens()) {
        stmtIns.setInt(1, os.getId());
        stmtIns.setInt(2, item.getIdServico());
        stmtIns.setInt(3, item.getQuantidade());
        stmtIns.setDouble(4, item.getValorUnitario());
        stmtIns.setDouble(5, item.getSubtotal());
        stmtIns.executeUpdate();
      }

      conn.commit(); // Confirma
      return true;

    } catch (SQLException e) {
      try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
      System.out.println("Erro ao atualizar OS: " + e.getMessage());
      return false;
    } finally {
      // Fechar conexões (omitido para brevidade, mas importante)
    }
  }
}
