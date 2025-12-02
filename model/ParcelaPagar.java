package model;
import java.time.LocalDate;

public class ParcelaPagar {
  private int numero;
  private LocalDate vencimento;
  private double valor;

  public ParcelaPagar(int numero, LocalDate vencimento, double valor) {
    this.numero = numero;
    this.vencimento = vencimento;
    this.valor = valor;
  }
  public int getNumero() { return numero; }
  public LocalDate getVencimento() { return vencimento; }
  public double getValor() { return valor; }
}
