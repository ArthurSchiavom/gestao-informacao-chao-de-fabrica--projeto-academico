package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaoerrosnotificacao.consultar;

import eapli.base.app.common.console.presentation.formatter.ConsoleTables;
import eapli.base.app.common.console.presentation.formatter.SimpleConsoleMessages;
import eapli.base.app.common.console.presentation.interaction.UserInteractionFlow;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.application.consultar.ConsultarErrosProcessamentoController;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.NotificacaoErroDTO;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.dto.TipoNotificacaoErroDTO;
import eapli.base.gestaoproducao.gestaolinhasproducao.dto.LinhaProducaoDTO;
import eapli.framework.presentation.console.AbstractUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConsultarErrosProcessamentoArquivadosUI extends AbstractUI {

    private void mostrarTabela(Collection collection) {
        String tabela = ConsoleTables.tabela(collection, true, 1);
        System.out.println(SimpleConsoleMessages.CLEAR_SCREEN + tabela + "\n\n");
    }

    private <T> void pedirParaSelecionar(Collection<T> collection, String nomeLowercasePluralItemASelecionar, ConsultarErrosProcessamentoController controller) {
        mostrarTabela(collection);
        List<T> selecao = UserInteractionFlow.solicitarEscolhaItensSeparadosPorVirgula(
                "Selecione os números dos itens " + nomeLowercasePluralItemASelecionar + " que deseja incluir nos resultados. " +
                        "Separe os números com vírgulas. Responda com 0 para selecionar todas.",
                ",",
                "\nApenas pode selecionar valores presentes na tabela apresentada",
                new ArrayList<>(collection), 1, true, 0);
        controller.selecionar(selecao);
    }

    private void prepararController(ConsultarErrosProcessamentoController controller) {
        List<LinhaProducaoDTO> linhasProducao = controller.linhasProducao();
        pedirParaSelecionar(linhasProducao, "linhas de produção", controller);

        List<TipoNotificacaoErroDTO> tipos = controller.tiposNotificaoErro();
        pedirParaSelecionar(tipos, "tipos de notificações", controller);

        controller.selecionarEstadoArquivado();
    }

    private void mostrarResultado(ConsultarErrosProcessamentoController controller) {
        List<NotificacaoErroDTO> resultado = controller.buscarResultado();
        if (resultado.isEmpty()) {
            System.out.println(SimpleConsoleMessages.CLEAR_SCREEN + "** RESULTADO **\n\nNão há notificações nas condições especificadas.\n\n");
            return;
        }
        String resultadoDisplay = ConsoleTables.tabela(resultado, false, 0);
        System.out.println(SimpleConsoleMessages.CLEAR_SCREEN + "** RESULTADO **\n\n" + resultadoDisplay + "\n\n");
    }

    @Override
    protected boolean doShow() {
        ConsultarErrosProcessamentoController controller = new ConsultarErrosProcessamentoController();

        prepararController(controller);
        mostrarResultado(controller);

        UserInteractionFlow.enterParaContinuar();
        return false;
    }

    @Override
    public String headline() {
        return "Consultar Erros de Processamento Arquivados";
    }
}
