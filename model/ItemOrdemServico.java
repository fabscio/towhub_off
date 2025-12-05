package model;

public class ItemOrdemServico {
    private int id; // ID do banco (opcional na criação)
    private int idServico;
    private String nomeServico; // Para exibir na tabela
    private int quantidade;
    private double valorUnitario;
    private double subtotal;

    // Construtor Vazio (Necessário para o Popup criar e setar depois)
    public ItemOrdemServico() {}

    // Construtor Completo
    public ItemOrdemServico(int idServico, String nomeServico, int quantidade, double valorUnitario) {
        this.idServico = idServico;
        this.nomeServico = nomeServico;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.subtotal = quantidade * valorUnitario;
    }

    // --- Getters e Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdServico() { return idServico; }
    public void setIdServico(int idServico) { this.idServico = idServico; }

    public String getNomeServico() { return nomeServico; }
    public void setNomeServico(String nomeServico) { this.nomeServico = nomeServico; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        atualizarSubtotal();
    }

    public double getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
        atualizarSubtotal();
    }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    // Método auxiliar para recalcular sempre que qtd ou valor mudar
    private void atualizarSubtotal() {
        this.subtotal = this.quantidade * this.valorUnitario;
    }
}
