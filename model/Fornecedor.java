package model;

public class Fornecedor {
    private int id;
    private String tipo;
    private String nome;
    private String cpfOuCnpj;
    private String nicho;
    private String telefone;
    private String email;
    private String endereco;

    public Fornecedor() {}

    public Fornecedor(String tipo, String nome, String cpfOuCnpj, String nicho,
                      String telefone, String email, String endereco) {
        this.tipo = tipo;
        this.nome = nome;
        this.cpfOuCnpj = cpfOuCnpj;
        this.nicho = nicho;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpfOuCnpj() { return cpfOuCnpj; }
    public void setCpfOuCnpj(String cpfOuCnpj) { this.cpfOuCnpj = cpfOuCnpj; }
    public String getNicho() { return nicho; }
    public void setNicho(String nicho) { this.nicho = nicho; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    // ESSENCIAL PARA O COMBOBOX MOSTRAR O NOME
    @Override
    public String toString() {
        return this.nome;
    }
}
