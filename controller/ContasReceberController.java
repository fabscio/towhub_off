package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import model.Cliente;
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
    @FXML private TableColumn<OrdemServico, String> colDataOs; // Adicionado para evitar erro no FXML se existir

    private ObservableList<OrdemServico> selecionadas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dtEmissao.setValue(LocalDate.now());

        carregarClientesPJ();
        configurarComboOS();

        cbStatus.setItems(FXCollections.observableArrayList("PENDENTE", "PAGO"));

        colIdOs.setCellValueFactory(new PropertyValueFactory<>("id"));
        colValorOs.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        // Se houver coluna de data no FXML, descomente:
        // colDataOs.setCellValueFactory(new PropertyValueFactory<>("dataEmissao"));

        tabelaOs.setItems(selecionadas);
    }

    private void carregarClientesPJ() {
        List<Cliente> todos = new ClienteDAO().listar();

        // CORREÇÃO: Filtra verificando se a String tipo é "PJ"
        List<Cliente> apenasPJ = todos.stream()
            .filter(c -> "PJ".equals(c.getTipo()))
            .collect(Collectors.toList());

        cbCliente.setItems(FXCollections.observableArrayList(apenasPJ));
    }

    private void configurarComboOS() {
        List<OrdemServico> pendentes = new OrdemServicoDAO().listarPendentes();
        cbOsDisponiveis.setItems(FXCollections.observableArrayList(pendentes));

        // Formatação visual do ComboBox
        cbOsDisponiveis.setConverter(new StringConverter<OrdemServico>() {
            @Override
            public String toString(OrdemServico os) {
                if (os == null) return null;
                return String.format("OS Nº %d - R$ %.2f", os.getId(), os.getValorTotal());
            }

            @Override
            public OrdemServico fromString(String string) {
                return null;
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
            configurarComboOS(); // Recarrega para remover as usadas
        }
    }
}
