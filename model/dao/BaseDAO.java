package model.dao;

import database.ConexaoFactory;
import model.Base;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDAO {

    public boolean salvar(Base b) {
        String sql = "INSERT INTO bases (cnpj, razao_social, endereco) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, b.getCnpj());
            stmt.setString(2, b.getRazaoSocial());
            stmt.setString(3, b.getEndereco());

            stmt.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao salvar base: " + e.getMessage());
            return false;
        }
    }

    public List<Base> listar() {
        List<Base> lista = new ArrayList<>();
        String sql = "SELECT * FROM bases";

        try (Connection conn = ConexaoFactory.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Base b = new Base();
                b.setId(rs.getInt("id"));
                b.setRazaoSocial(rs.getString("razao_social"));
                b.setCnpj(rs.getString("cnpj"));
                b.setEndereco(rs.getString("endereco"));
                lista.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
