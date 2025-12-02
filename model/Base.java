package model;

public class Base {
    private int id;
    private String cnpj;
    private String razaoSocial;
    private String endereco;

    // Construtor vazio
    public Base() {}

    // Construtor Completo (usado no cadastro)
    public Base(String cnpj, String razaoSocial, String endereco) {
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.endereco = endereco;
    }

    // Construtor Auxiliar (usado na listagem do DAO)
    public Base(int id, String razaoSocial) {
        this.id = id;
        this.razaoSocial = razaoSocial;
    }

    // --- Getters e Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    // Importante para o ComboBox mostrar o nome
    @Override
    public String toString() {
        return this.razaoSocial;
    }
}
