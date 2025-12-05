package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter; // Import necessário
import model.Cliente;
import model.PessoaJuridica; // Import necessário
import model.LoteCobranca;
import model.OrdemServico;
import model.dao.ClienteDAO;
import model.dao.ContasReceberDAO;
import model.dao.OrdemServicoDAO;
import util.Alerta;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ContasReceberController {

    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private ComboBox<String> cbStatus;
    @FXML private DatePicker dtEmissao, dtVencimento;
    @FXML private Label lblTotalLote;

    @FXML private ComboBox<OrdemServico> cbOsDisponiveis;
    @FXML private TableView<OrdemServico> tabelaOs;
    @FXML private TableColumn<OrdemServico, Integer> colIdOs;
    @FXML private TableColumn<OrdemServico, Double> colValorOs;

    private ObservableList<OrdemServico> selecionadas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dtEmissao.setValue(LocalDate.now());

        carregarClientesPJ(); // Lógica separada para filtrar PJ
        configurarComboOS();  // Lógica separada para formatar visualização

        cbStatus.setItems(FXCollections.observableArrayList("PENDENTE", "PAGO"));

        colIdOs.setCellValueFactory(new PropertyValueFactory<>("id"));
        colValorOs.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        tabelaOs.setItems(selecionadas);
    }

    private void carregarClientesPJ() {
        List<Cliente> todos = new ClienteDAO().listar();

        // FILTRO: Apenas Pessoa Jurídica
        List<Cliente> apenasPJ = todos.stream()
            .filter(c -> c instanceof PessoaJuridica)
            .collect(Collectors.toList());

        cbCliente.setItems(FXCollections.observableArrayList(apenasPJ));
    }

    private void configurarComboOS() {
        List<OrdemServico> pendentes = new OrdemServicoDAO().listarPendentes();
        cbOsDisponiveis.setItems(FXCollections.observableArrayList(pendentes));

        // FORMATAÇÃO: Mostra "OS Nº X - R$ Y" em vez do caminho da memória
        cbOsDisponiveis.setConverter(new StringConverter<OrdemServico>() {
            @Override
            public String toString(OrdemServico os) {
                if (os == null) return null;
                return String.format("OS Nº %d - Total: R$ %.2f", os.getId(), os.getValorTotal());
            }

            @Override
            public OrdemServico fromString(String string) {
                return null; // Não necessário para ComboBox de seleção
            }
        });
    }

    @FXML
    public void acaoAdicionarOs() {
        OrdemServico os = cbOsDisponiveis.getValue();
        if (os != null && !selecionadas.contains(os)) {
            selecionadas.add(os);
            atualizarTotal();
        }
    }

    @FXML
    public void acaoRemoverOs() {
        OrdemServico item = tabelaOs.getSelectionModel().getSelectedItem();
        if (item != null) {
            selecionadas.remove(item);
            atualizarTotal();
        }
    }

    private void atualizarTotal() {
        double total = selecionadas.stream().mapToDouble(OrdemServico::getValorTotal).sum();
        lblTotalLote.setText(String.format("R$ %.2f", total));
    }

    @FXML
    public void acaoSalvar() {
        if(cbCliente.getValue() == null) {
            Alerta.mostrarErro("Erro", "Selecione o Cliente.");
            return;
        }

        if (selecionadas.isEmpty()) {
            Alerta.mostrarErro("Erro", "Selecione ao menos uma OS.");
            return;
        }

        double total = selecionadas.stream().mapToDouble(OrdemServico::getValorTotal).sum();

        LoteCobranca lote = new LoteCobranca(cbCliente.getValue().getId(), dtEmissao.getValue(), dtVencimento.getValue(),
                                             cbStatus.getValue(), total);

        for (OrdemServico m : selecionadas) {
            lote.getIdsOs().add(m.getId());
        }

        if (new ContasReceberDAO().salvar(lote)) {
            Alerta.mostrarSucesso("Sucesso", "Fatura Gerada!");
            selecionadas.clear();
            atualizarTotal();
            // Recarrega pendentes atualizados
            configurarComboOS();
        }
    }
}
