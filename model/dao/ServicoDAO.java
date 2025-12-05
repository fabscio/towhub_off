package model.dao;

import database.ConexaoFactory;
import model.Servico;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

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

    // NOVO MÉTODO
    public List<Servico> listar() {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicos ORDER BY nome";

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Servico s = new Servico();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setValorPadrao(rs.getDouble("valor_padrao"));
                lista.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
