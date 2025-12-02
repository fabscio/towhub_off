package model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoteCobranca {
  private int idCliente;
  private LocalDate emissao;
  private LocalDate vencimento;
  private String status;
  private double valorTotal;
  private List<Integer> idsOs = new ArrayList<>();

  public LoteCobranca(int idCliente, LocalDate emissao, LocalDate vencimento, String status, double valorTotal) {
    this.idCliente = idCliente;
    this.emissao = emissao;
    this.vencimento = vencimento;
    this.status = status;
    this.valorTotal = valorTotal;
  }
  public List<Integer> getIdsOs() { return idsOs; }
  public int getIdCliente() { return idCliente; }
  public LocalDate getEmissao() { return emissao; }
  public LocalDate getVencimento() { return vencimento; }
  public String getStatus() { return status; }
  public double getValorTotal() { return valorTotal; }
}
