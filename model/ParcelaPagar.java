package model;
import java.time.LocalDate;

public class ParcelaPagar {
  private int id;
  private int numeroParcela;
  private LocalDate vencimento;
  private double valor;
  private String status;

  public ParcelaPagar(int numeroParcela, LocalDate vencimento, double valor) {
    this.numeroParcela = numeroParcela;
    this.vencimento = vencimento;
    this.valor = valor;
    this.status = "PENDENTE";
  }
  // Getters e Setters
  public int getNumeroParcela() { return numeroParcela; }
  public LocalDate getVencimento() { return vencimento; }
  public double getValor() { return valor; }
  public String getStatus() { return status; }
}
