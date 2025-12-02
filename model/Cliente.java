package model;

public class Cliente {

  // Atributos da classe
  private int id;
  private String tipo;        // "PF" ou "PJ"
  private String nome;
  private String cpfOuCnpj;
  private String telefone;
  private String email;
  private String endereco;
  private int idBase;         // Vínculo com a Base Operacional

  // Construtor vazio (obrigatório para algumas bibliotecas)
  public Cliente() {
  }

  // Construtor completo para criar novos clientes
  public Cliente(String tipo, String nome, String cpfOuCnpj, String telefone, 
                 String email, String endereco, int idBase) {
    this.tipo = tipo;
    this.nome = nome;
    this.cpfOuCnpj = cpfOuCnpj;
    this.telefone = telefone;
    this.email = email;
    this.endereco = endereco;
    this.idBase = idBase;
  }

  // --- Métodos de Acesso (Getters e Setters) ---

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpfOuCnpj() {
    return cpfOuCnpj;
  }

  public void setCpfOuCnpj(String cpfOuCnpj) {
    this.cpfOuCnpj = cpfOuCnpj;
  }

  public String getTelefone() {
    return telefone;
  }

  public void setTelefone(String telefone) {
    this.telefone = telefone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEndereco() {
    return endereco;
  }

  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  public int getIdBase() {
    return idBase;
  }

  public void setIdBase(int idBase) {
    this.idBase = idBase;
  }
}
