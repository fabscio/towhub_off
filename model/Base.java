package model;

public class Base {
  private int id;
  private String cnpj;
  private String razaoSocial;
  private String endereco;

  public Base() {}

  public Base(String cnpj, String razaoSocial, String endereco) {
    this.cnpj = cnpj;
    this.razaoSocial = razaoSocial;
    this.endereco = endereco;
  }

  // Getters e Setters
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }
  public String getCnpj() { return cnpj; }
  public void setCnpj(String cnpj) { this.cnpj = cnpj; }
  public String getRazaoSocial() { return razaoSocial; }
  public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
  public String getEndereco() { return endereco; }
  public void setEndereco(String endereco) { this.endereco = endereco; }
  
  // Para aparecer o nome bonito no ComboBox
  @Override
  public String toString() { return razaoSocial; }
}
