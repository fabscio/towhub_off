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
  
  // IDs (FKs)
  private int idCliente;
  private int idBase;
  private int idMotorista;
  private int idAnalista;

  private String formaPagamento;
  private double valorTotal;

  private List<ItemOrdemServico> itens = new ArrayList<>();

  public OrdemServico() {}

  // Adiciona e Soma
  public void adicionarItem(ItemOrdemServico item) {
    this.itens.add(item);
    this.valorTotal += item.getSubtotal();
  }

  // Remove e Subtrai (NOVO)
  public void removerItem(ItemOrdemServico item) {
    if (this.itens.remove(item)) {
        this.valorTotal -= item.getSubtotal();
        if (this.valorTotal < 0) this.valorTotal = 0; // Segurança
    }
  }

  // --- Getters e Setters ---
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

  // Total: permitimos setar manual (vinda do banco) ou calculada
  public double getValorTotal() { return valorTotal; }
  public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

  public int getIdCliente() { return idCliente; }
  public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
  public int getIdBase() { return idBase; }
  public void setIdBase(int idBase) { this.idBase = idBase; }

  public List<ItemOrdemServico> getItens() { return itens; }

  // Outros getters e setters conforme necessário para Motorista, Analista, etc.
  public int getIdMotorista() { return idMotorista; }
  public void setIdMotorista(int idMotorista) { this.idMotorista = idMotorista; }
  public int getIdAnalista() { return idAnalista; }
  public void setIdAnalista(int idAnalista) { this.idAnalista = idAnalista; }
  public String getFormaPagamento() { return formaPagamento; }
  public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
  public String getOrigem() { return origem; }
  public void setOrigem(String origem) { this.origem = origem; }
  public String getDestino() { return destino; }
  public void setDestino(String destino) { this.destino = destino; }
  public int getPrazo() { return prazo; }
  public void setPrazo(int prazo) { this.prazo = prazo; }
  public String getTipoSolicitacao() { return tipoSolicitacao; }
  public void setTipoSolicitacao(String tipoSolicitacao) { this.tipoSolicitacao = tipoSolicitacao; }
}
