package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdemServico {
  private int id;
  private LocalDate dataEmissao;
  private int prazo;
  private String veiculoModelo;
  private String veiculoPlaca;
  private String contato;
  private String tipoSolicitacao;
  private String origem;
  private String destino;
  
  // IDs (Chaves Estrangeiras)
  private int idCliente;
  private int idBase;
  private int idMotorista;
  private int idAnalista;
  
  private String formaPagamento;
  private double valorTotal;
  
  // Lista de Itens (O detalhe)
  private List<ItemOrdemServico> itens = new ArrayList<>();

  public OrdemServico() {}

  // Método para adicionar item na lista
  public void adicionarItem(ItemOrdemServico item) {
    this.itens.add(item);
    this.valorTotal += item.getSubtotal();
  }
  
  // Getters e Setters (Resumido para os principais)
  public LocalDate getDataEmissao() { return dataEmissao; }
  public void setDataEmissao(LocalDate data) { this.dataEmissao = data; }
  
  public List<ItemOrdemServico> getItens() { return itens; }
  
  public double getValorTotal() { return valorTotal; }
  public void setValorTotal(double total) { this.valorTotal = total; }

  // ... Adicione os Getters/Setters para todos os campos acima ...
  public void setIdCliente(int id) { this.idCliente = id; }
  public int getIdCliente() { return idCliente; }
  public void setIdBase(int id) { this.idBase = id; }
  public int getIdBase() { return idBase; }
  // etc...
}
