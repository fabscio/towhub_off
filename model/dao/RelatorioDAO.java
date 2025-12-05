package model.dao;

import database.ConexaoFactory;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    // --- RELATÓRIO DE OS ---
    // Busca na View: relatorio_os
    public List<String[]> buscarOS(String base, String cliente, LocalDate inicio, LocalDate fim) {
        List<String[]> lista = new ArrayList<>();

        // Começa com 1=1 para facilitar a concatenação dos "AND" depois
        String sql = "SELECT * FROM relatorio_os WHERE 1=1";

        // Concatenação simples de Strings (Estilo Estudante)
        if (base != null && !base.isEmpty()) {
            sql += " AND base = '" + base + "'";
        }

        // Usando LIKE para buscar parte do nome
        if (cliente != null && !cliente.isEmpty()) {
            sql += " AND cliente LIKE '%" + cliente + "%'";
        }

        if (inicio != null) {
            sql += " AND data >= '" + inicio + "'";
        }

        if (fim != null) {
            sql += " AND data <= '" + fim + "'";
        }

        try (Connection conn = ConexaoFactory.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Monta o array de strings na ordem que a tabela espera
                lista.add(new String[]{
                    String.valueOf(rs.getInt("id")),
                    String.valueOf(rs.getDate("data")),
                    rs.getString("cliente"),
                    rs.getString("motorista"),
                    rs.getString("veiculo"),
                    String.valueOf(rs.getDouble("valor_total"))
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // --- RELATÓRIO DE PAGAMENTOS ---
    // Busca na View: relatorio_pagamentos
    public List<String[]> buscarPagamentos(String base, String fornecedor, LocalDate inicio, LocalDate fim, String status) {
        List<String[]> lista = new ArrayList<>();

        String sql = "SELECT * FROM relatorio_pagamentos WHERE 1=1";

        if (base != null && !base.isEmpty()) {
            sql += " AND base = '" + base + "'";
        }

        if (fornecedor != null && !fornecedor.isEmpty()) {
            sql += " AND fornecedor LIKE '%" + fornecedor + "%'";
        }

        if (inicio != null) {
            sql += " AND data_vencimento >= '" + inicio + "'";
        }

        if (fim != null) {
            sql += " AND data_vencimento <= '" + fim + "'";
        }

        if (status != null && !"Todos".equals(status)) {
            sql += " AND status = '" + status + "'";
        }

        try (Connection conn = ConexaoFactory.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("id_pagamento"),
                    rs.getString("fornecedor"),
                    String.valueOf(rs.getDate("data_vencimento")),
                    rs.getString("status"),
                    String.valueOf(rs.getDouble("valor"))
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // --- RELATÓRIO DE RECEBIMENTOS ---
    // Busca na View: relatorio_recebimentos
    public List<String[]> buscarRecebimentos(String base, String cliente, LocalDate inicio, LocalDate fim, String status) {
        List<String[]> lista = new ArrayList<>();

        String sql = "SELECT * FROM relatorio_recebimentos WHERE 1=1";

        if (base != null && !base.isEmpty()) {
            sql += " AND base = '" + base + "'";
        }

        if (cliente != null && !cliente.isEmpty()) {
            sql += " AND cliente LIKE '%" + cliente + "%'";
        }

        if (inicio != null) {
            sql += " AND data_vencimento >= '" + inicio + "'";
        }

        if (fim != null) {
            sql += " AND data_vencimento <= '" + fim + "'";
        }

        if (status != null && !"Todos".equals(status)) {
            sql += " AND status = '" + status + "'";
        }

        try (Connection conn = ConexaoFactory.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new String[]{
                    String.valueOf(rs.getInt("id_lote")),
                    rs.getString("cliente"),
                    String.valueOf(rs.getDate("data_vencimento")),
                    rs.getString("status"),
                    String.valueOf(rs.getDouble("valor_total"))
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
