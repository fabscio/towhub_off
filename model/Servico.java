package model;

public class Servico {
  private int id;
  private String nome;
  private double valorPadrao;

  // Construtor Vazio
  public Servico() {}

  public Servico(String nome, double valorPadrao) {
    this.nome = nome;
    this.valorPadrao = valorPadrao;
  }

  public int getId() { return id; }
  public void setId(int id) { this.id = id; }
  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }
  public double getValorPadrao() { return valorPadrao; }
  public void setValorPadrao(double valorPadrao) { this.valorPadrao = valorPadrao; }

  // ToString para ComboBox de serviços
  @Override
  public String toString() {
      return this.nome;
  }
}
