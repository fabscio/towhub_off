package model;

public class Funcionario {
  private int id;
  private String nome;
  private String cpf;
  private String senha;
  private String funcao; // Motorista, Analista, Admin
  private int idBase;
  private double salarioBase;
  private String cargaHoraria;
  private String telefone;
  private String endereco;

  // Específico Motorista
  private String cnh;
  private String categoriaCnh;

  public Funcionario() {}

  public Funcionario(String nome, String cpf, String senha, String funcao, int idBase,
                     double salarioBase, String cargaHoraria, String telefone, String endereco,
                     String cnh, String categoriaCnh) {
    this.nome = nome;
    this.cpf = cpf;
    this.senha = senha;
    this.funcao = funcao;
    this.idBase = idBase;
    this.salarioBase = salarioBase;
    this.cargaHoraria = cargaHoraria;
    this.telefone = telefone;
    this.endereco = endereco;
    this.cnh = cnh;
    this.categoriaCnh = categoriaCnh;
  }

  // Getters e Setters
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }

  public String getCpf() { return cpf; }
  public void setCpf(String cpf) { this.cpf = cpf; }

  public String getSenha() { return senha; }
  public void setSenha(String senha) { this.senha = senha; }

  public String getFuncao() { return funcao; }
  public void setFuncao(String funcao) { this.funcao = funcao; }

  public int getIdBase() { return idBase; }
  public void setIdBase(int idBase) { this.idBase = idBase; }

  public double getSalarioBase() { return salarioBase; }
  public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }

  public String getCargaHoraria() { return cargaHoraria; }
  public void setCargaHoraria(String cargaHoraria) { this.cargaHoraria = cargaHoraria; }

  public String getTelefone() { return telefone; }
  public void setTelefone(String telefone) { this.telefone = telefone; }

  public String getEndereco() { return endereco; }
  public void setEndereco(String endereco) { this.endereco = endereco; }

  public String getCnh() { return cnh; }
  public void setCnh(String cnh) { this.cnh = cnh; }

  public String getCategoriaCnh() { return categoriaCnh; }
  public void setCategoriaCnh(String categoriaCnh) { this.categoriaCnh = categoriaCnh; }

  @Override
  public String toString() {
      return this.nome; // Para aparecer bonito no ComboBox
  }
}
