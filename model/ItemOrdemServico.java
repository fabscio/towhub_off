package model;

public class ItemOrdemServico {
  private int id;
  private int idServico;
  private String nomeServico; // Apenas para exibição na tabela
  private int quantidade;
  private double valorUnitario;
  private double subtotal;

  public ItemOrdemServico(int idServico, String nomeServico, int quantidade, double valorUnitario) {
    this.idServico = idServico;
    this.nomeServico = nomeServico;
    this.quantidade = quantidade;
    this.valorUnitario = valorUnitario;
    this.subtotal = quantidade * valorUnitario;
  }

  public int getIdServico() { return idServico; }
  public String getNomeServico() { return nomeServico; }
  public int getQuantidade() { return quantidade; }
  public double getValorUnitario() { return valorUnitario; }
  public double getSubtotal() { return subtotal; }
}
