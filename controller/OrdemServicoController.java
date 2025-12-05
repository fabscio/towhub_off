package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.*;
import model.dao.*;
import util.Alerta;

import java.time.LocalDate;
import java.util.Optional;

public class OrdemServicoController {

    // --- Campos do Formulário ---
    @FXML private TextField txtId;
    @FXML private DatePicker dtData;
    @FXML private ComboBox<String> cbSolicitacao;

    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private TextField txtContato;
    @FXML private ComboBox<Base> cbBase;

    @FXML private TextField txtVeiculo;
    @FXML private TextField txtPlaca;

    // Campos que dependem se é PJ ou PF
    @FXML private TextField txtPrazo;
    @FXML private ComboBox<String> cbPagamento;

    @FXML private ComboBox<Funcionario> cbMotorista;
    @FXML private ComboBox<Funcionario> cbAnalista;

    @FXML private TextField txtOrigem;
    @FXML private TextField txtDestino;

    // --- Tabela de Serviços ---
    @FXML private TableView<ItemOrdemServico> tabelaServicos;
    @FXML private TableColumn<ItemOrdemServico, String> colServico;
    @FXML private TableColumn<ItemOrdemServico, Integer> colQtd;
    @FXML private TableColumn<ItemOrdemServico, Double> colValor;
    @FXML private TableColumn<ItemOrdemServico, Double> colSubtotal;

    @FXML private Label lblValorTotal;

    // --- Dados Internos ---
    private ObservableList<ItemOrdemServico> listaItens = FXCollections.observableArrayList();
    private OrdemServico osAtual;

    // Listas de Pagamento
    private final ObservableList<String> pgtoGeral = FXCollections.observableArrayList("Cartão Crédito", "Cartão Débito", "Dinheiro/Pix");
    private final ObservableList<String> pgtoPJ = FXCollections.observableArrayList("Faturado", "Cartão Crédito", "Cartão Débito", "Dinheiro/Pix");

    @FXML
    public void initialize() {
        osAtual = new OrdemServico();
        dtData.setValue(LocalDate.now());

        configurarTabela();
        carregarCombos();
        configurarLogicaPJ(); // Nova lógica de PJ
    }

    private void configurarTabela() {
        colServico.setCellValueFactory(new PropertyValueFactory<>("nomeServico"));
        colQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        tabelaServicos.setItems(listaItens);
    }

    // Configura visibilidade de prazo e opções de pagamento
    private void configurarLogicaPJ() {
        // Inicialmente desabilita prazo e usa lista básica
        txtPrazo.setDisable(true);
        cbPagamento.setItems(pgtoGeral);

        cbCliente.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                if (newVal instanceof PessoaJuridica) {
                    // É PJ: Habilita Faturado e Prazo
                    cbPagamento.setItems(pgtoPJ);
                    txtPrazo.setDisable(false);
                } else {
                    // É PF: Apenas pagamentos à vista e trava Prazo
                    cbPagamento.setItems(pgtoGeral);
                    txtPrazo.setDisable(true);
                    txtPrazo.clear();

                    // Se estava selecionado "Faturado", limpa a seleção
                    if ("Faturado".equals(cbPagamento.getValue())) {
                        cbPagamento.getSelectionModel().clearSelection();
                    }
                }
            }
        });
    }

    private void carregarCombos() {
        cbCliente.setItems(FXCollections.observableArrayList(new ClienteDAO().listar()));
        cbBase.setItems(FXCollections.observableArrayList(new BaseDAO().listar()));

        FuncionarioDAO funcDao = new FuncionarioDAO();
        cbMotorista.setItems(FXCollections.observableArrayList(funcDao.listarPorFuncao("Motorista")));
        cbAnalista.setItems(FXCollections.observableArrayList(funcDao.listarPorFuncao("Analista")));

        cbSolicitacao.setItems(FXCollections.observableArrayList("Telefone", "WhatsApp", "Email", "Seguradora"));

        // cbPagamento é configurado dinamicamente no configurarLogicaPJ()
    }

    @FXML
    public void handleAdicionarServico() {
        Dialog<ItemOrdemServico> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Serviço");
        dialog.setHeaderText("Selecione o serviço e a quantidade");

        ButtonType btnAdicionar = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnAdicionar, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 50, 10, 10));

        ComboBox<Servico> cbServicoPopup = new ComboBox<>();
        cbServicoPopup.setPromptText("Selecione...");
        cbServicoPopup.setPrefWidth(200);
        cbServicoPopup.setItems(FXCollections.observableArrayList(new ServicoDAO().listar()));

        Spinner<Integer> spinnerQtd = new Spinner<>(1, 100, 1);
        Label lblValorUnit = new Label("Valor Unit: R$ 0,00");

        cbServicoPopup.setOnAction(e -> {
            Servico s = cbServicoPopup.getValue();
            if (s != null) {
                lblValorUnit.setText(String.format("Valor Unit: R$ %.2f", s.getValorPadrao()));
            }
        });

        grid.add(new Label("Serviço:"), 0, 0);
        grid.add(cbServicoPopup, 1, 0);
        grid.add(new Label("Quantidade:"), 0, 1);
        grid.add(spinnerQtd, 1, 1);
        grid.add(lblValorUnit, 1, 2);

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(cbServicoPopup::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnAdicionar) {
                Servico s = cbServicoPopup.getValue();
                if (s != null) {
                    ItemOrdemServico item = new ItemOrdemServico();
                    item.setIdServico(s.getId());
                    item.setNomeServico(s.getNome());
                    item.setValorUnitario(s.getValorPadrao());
                    item.setQuantidade(spinnerQtd.getValue());
                    return item;
                }
            }
            return null;
        });

        Optional<ItemOrdemServico> result = dialog.showAndWait();
        result.ifPresent(item -> {
            listaItens.add(item);
            osAtual.adicionarItem(item);
            atualizarTotalNaTela();
        });
    }

    @FXML
    public void handleRemoverServico() {
        ItemOrdemServico item = tabelaServicos.getSelectionModel().getSelectedItem();
        if (item != null) {
            listaItens.remove(item);
            osAtual.removerItem(item);
            atualizarTotalNaTela();
        } else {
            Alerta.mostrarErro("Erro", "Selecione um item para remover.");
        }
    }

    private void atualizarTotalNaTela() {
        lblValorTotal.setText(String.format("R$ %.2f", osAtual.getValorTotal()));
    }

    @FXML
    public void handleConcluir() {
        if (cbCliente.getValue() == null || cbBase.getValue() == null) {
            Alerta.mostrarErro("Erro", "Preencha o Cliente e a Base!");
            return;
        }
        if (listaItens.isEmpty()) {
            Alerta.mostrarErro("Erro", "Adicione pelo menos um serviço!");
            return;
        }

        osAtual.setDataEmissao(dtData.getValue());
        osAtual.setIdCliente(cbCliente.getValue().getId());
        osAtual.setIdBase(cbBase.getValue().getId());

        if (cbMotorista.getValue() != null) osAtual.setIdMotorista(cbMotorista.getValue().getId());
        if (cbAnalista.getValue() != null) osAtual.setIdAnalista(cbAnalista.getValue().getId());

        osAtual.setVeiculoModelo(txtVeiculo.getText());
        osAtual.setVeiculoPlaca(txtPlaca.getText());
        osAtual.setContato(txtContato.getText());
        osAtual.setTipoSolicitacao(cbSolicitacao.getValue());
        osAtual.setFormaPagamento(cbPagamento.getValue());
        osAtual.setOrigem(txtOrigem.getText());
        osAtual.setDestino(txtDestino.getText());

        try {
            if (!txtPrazo.isDisabled() && !txtPrazo.getText().isEmpty())
                osAtual.setPrazo(Integer.parseInt(txtPrazo.getText()));
            else
                osAtual.setPrazo(0); // Sem prazo se for PF ou vazio
        } catch (NumberFormatException e) { osAtual.setPrazo(0); }

        if (new OrdemServicoDAO().salvar(osAtual)) {
            Alerta.mostrarSucesso("Sucesso", "Ordem de Serviço criada com sucesso!");
            limparTela();
        } else {
            Alerta.mostrarErro("Erro", "Falha ao salvar no banco de dados.");
        }
    }

    private void limparTela() {
        osAtual = new OrdemServico();
        listaItens.clear();
        atualizarTotalNaTela();

        txtVeiculo.clear(); txtPlaca.clear(); txtContato.clear();
        txtOrigem.clear(); txtDestino.clear(); txtPrazo.clear();

        cbCliente.getSelectionModel().clearSelection();
        cbMotorista.getSelectionModel().clearSelection();
        cbAnalista.getSelectionModel().clearSelection();
        cbPagamento.getSelectionModel().clearSelection();
    }
}
