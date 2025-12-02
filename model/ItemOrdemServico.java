package model;

public class ItemOrdemServico {
  private int id;
  private int idOrdemServico;
  private int idServico;       // Referência ao Serviço
  private String nomeServico;  // Apenas para exibir na Tabela
  private int quantidade;
  private double valorUnitario;
  
  public ItemOrdemServico(int idServico, String nomeServico, int quantidade, double valorUnitario) {
    this.idServico = idServico;
    this.nomeServico = nomeServico;
    this.quantidade = quantidade;
    this.valorUnitario = valorUnitario;
  }

  // Getters, Setters e Cálculo do Subtotal
  public double getSubtotal() { return quantidade * valorUnitario; }
  
  public String getNomeServico() { return nomeServico; }
  public int getQuantidade() { return quantidade; }
  public double getValorUnitario() { return valorUnitario; }
  public int getIdServico() { return idServico; }
}
