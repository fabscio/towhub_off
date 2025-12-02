package model;

public class Servico {
  private int id;
  private String nome;
  private double valorPadrao;

  public Servico(String nome, double valorPadrao) {
    this.nome = nome;
    this.valorPadrao = valorPadrao;
  }
  // Getters (getNome, getValorPadrao)
  public String getNome() { return nome; }
  public double getValorPadrao() { return valorPadrao; }
}
