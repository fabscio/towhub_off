package model.dao;

import database.ConexaoFactory;
import model.OrdemServico;
import model.ItemOrdemServico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrdemServicoDAO {

    public boolean salvar(OrdemServico os) {
        Connection conn = null;
        String sqlOs = "INSERT INTO ordem_servico (data_emissao, id_cliente, id_base, veiculo_modelo, veiculo_placa, contato_segurado, valor_total) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";
        String sqlItem = "INSERT INTO itens_os (id_ordem_servico, id_servico, quantidade, valor_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try {
            conn = ConexaoFactory.getConexao();
            conn.setAutoCommit(false); // Transação

            PreparedStatement stmtOs = conn.prepareStatement(sqlOs);
            stmtOs.setDate(1, Date.valueOf(os.getDataEmissao()));
            stmtOs.setInt(2, os.getIdCliente());
            stmtOs.setInt(3, os.getIdBase());
            stmtOs.setString(4, os.getVeiculoModelo());
            stmtOs.setString(5, os.getVeiculoPlaca());
            stmtOs.setString(6, os.getContato());
            stmtOs.setDouble(7, os.getValorTotal());

            ResultSet rs = stmtOs.executeQuery();
            int idGerado = 0;
            if (rs.next()) idGerado = rs.getInt(1);

            PreparedStatement stmtItem = conn.prepareStatement(sqlItem);
            for (ItemOrdemServico item : os.getItens()) {
                stmtItem.setInt(1, idGerado);
                stmtItem.setInt(2, item.getIdServico());
                stmtItem.setInt(3, item.getQuantidade());
                stmtItem.setDouble(4, item.getValorUnitario());
                stmtItem.setDouble(5, item.getSubtotal());
                stmtItem.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ex) {}
            e.printStackTrace();
            return false;
        }
    }

    public OrdemServico buscarPorId(int id) {
        OrdemServico os = null;
        String sql = "SELECT * FROM ordem_servico WHERE id = ?";
        String sqlItens = "SELECT i.*, s.nome FROM itens_os i JOIN servicos s ON i.id_servico = s.id WHERE id_ordem_servico = ?";

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement stmtItens = conn.prepareStatement(sqlItens)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                os = new OrdemServico();
                os.setId(rs.getInt("id"));
                os.setDataEmissao(rs.getDate("data_emissao").toLocalDate());
                os.setVeiculoModelo(rs.getString("veiculo_modelo"));
                os.setVeiculoPlaca(rs.getString("veiculo_placa"));
                os.setContato(rs.getString("contato_segurado"));
                os.setValorTotal(rs.getDouble("valor_total"));
                os.setIdCliente(rs.getInt("id_cliente"));
                os.setIdBase(rs.getInt("id_base"));
            }

            if (os != null) {
                stmtItens.setInt(1, id);
                ResultSet rsItens = stmtItens.executeQuery();
                while(rsItens.next()) {
                    os.adicionarItem(new ItemOrdemServico(
                        rsItens.getInt("id_servico"),
                        rsItens.getString("nome"),
                        rsItens.getInt("quantidade"),
                        rsItens.getDouble("valor_unitario")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return os;
    }

    public boolean atualizar(OrdemServico os) {
        Connection conn = null;
        // Estratégia simples: Update cabeçalho, Delete itens antigos, Insert itens novos
        String sqlOs = "UPDATE ordem_servico SET data_emissao=?, veiculo_modelo=?, veiculo_placa=?, contato_segurado=?, valor_total=? WHERE id=?";
        String sqlDel = "DELETE FROM itens_os WHERE id_ordem_servico=?";
        String sqlIns = "INSERT INTO itens_os (id_ordem_servico, id_servico, quantidade, valor_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try {
            conn = ConexaoFactory.getConexao();
            conn.setAutoCommit(false);

            PreparedStatement stmtOs = conn.prepareStatement(sqlOs);
            stmtOs.setDate(1, Date.valueOf(os.getDataEmissao()));
            stmtOs.setString(2, os.getVeiculoModelo());
            stmtOs.setString(3, os.getVeiculoPlaca());
            stmtOs.setString(4, os.getContato());
            stmtOs.setDouble(5, os.getValorTotal());
            stmtOs.setInt(6, os.getId());
            stmtOs.executeUpdate();

            PreparedStatement stmtDel = conn.prepareStatement(sqlDel);
            stmtDel.setInt(1, os.getId());
            stmtDel.executeUpdate();

            PreparedStatement stmtIns = conn.prepareStatement(sqlIns);
            for(ItemOrdemServico item : os.getItens()) {
                stmtIns.setInt(1, os.getId());
                stmtIns.setInt(2, item.getIdServico());
                stmtIns.setInt(3, item.getQuantidade());
                stmtIns.setDouble(4, item.getValorUnitario());
                stmtIns.setDouble(5, item.getSubtotal());
                stmtIns.executeUpdate();
            }

            conn.commit();
            return true;
        } catch(SQLException e) {
            try { if(conn!=null) conn.rollback(); } catch(Exception ex){}
            e.printStackTrace();
            return false;
        }
    }

    // Adicionar este método dentro da classe OrdemServicoDAO existente

    // Busca OS que ainda não foram faturadas (id_lote IS NULL)
    public ArrayList<OrdemServico> listarPendentes() {
        ArrayList<OrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordem_servico WHERE id_lote IS NULL ORDER BY data_emissao";

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrdemServico os = new OrdemServico();
                os.setId(rs.getInt("id"));
                os.setDataEmissao(rs.getDate("data_emissao").toLocalDate());
                os.setValorTotal(rs.getDouble("valor_total"));
                // ... preencher outros campos relevantes para exibição
                lista.add(os);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
