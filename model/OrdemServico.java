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
  
  // Chaves Estrangeiras
  private int idCliente;
  private int idBase;
  private int idMotorista;
  private int idAnalista;

  private String formaPagamento;
  private double valorTotal;

  private List<ItemOrdemServico> itens = new ArrayList<>();

  public OrdemServico() {}

  public void adicionarItem(ItemOrdemServico item) {
    this.itens.add(item);
    this.valorTotal += item.getSubtotal();
  }

  // Getters e Setters
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }
  public LocalDate getDataEmissao() { return dataEmissao; }
  public void setDataEmissao(LocalDate dataEmissao) { this.dataEmissao = dataEmissao; }
  public String getVeiculoModelo() { return veiculoModelo; }
  public void setVeiculoModelo(String veiculoModelo) { this.veiculoModelo = veiculoModelo; }
  public String getVeiculoPlaca() { return veiculoPlaca; }
  public void setVeiculoPlaca(String veiculoPlaca) { this.veiculoPlaca = veiculoPlaca; }
  public String getContato() { return contato; }
  public void setContato(String contato) { this.contato = contato; }
  public double getValorTotal() { return valorTotal; }
  public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
  public int getIdCliente() { return idCliente; }
  public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
  public int getIdBase() { return idBase; }
  public void setIdBase(int idBase) { this.idBase = idBase; }
  public List<ItemOrdemServico> getItens() { return itens; }

  // Adicione os outros getters/setters conforme necessidade (motorista, analista, etc)
}
