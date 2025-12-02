package model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContaPagar {
  private int id;
  private int idFornecedor;
  private int idBase;
  private LocalDate dataCompra;
  private String descricao; // Opcional no SQL, mas bom ter
  private double valorTotal;
  private List<ParcelaPagar> parcelas = new ArrayList<>();

  public ContaPagar() {}

  public void adicionarParcela(ParcelaPagar p) {
    this.parcelas.add(p);
    this.valorTotal += p.getValor();
  }

  // Getters e Setters (Resumido)
  public int getIdFornecedor() { return idFornecedor; }
  public void setIdFornecedor(int id) { this.idFornecedor = id; }
  public int getIdBase() { return idBase; }
  public void setIdBase(int id) { this.idBase = id; }
  public LocalDate getDataCompra() { return dataCompra; }
  public void setDataCompra(LocalDate data) { this.dataCompra = data; }
  public double getValorTotal() { return valorTotal; }
  public List<ParcelaPagar> getParcelas() { return parcelas; }
}
