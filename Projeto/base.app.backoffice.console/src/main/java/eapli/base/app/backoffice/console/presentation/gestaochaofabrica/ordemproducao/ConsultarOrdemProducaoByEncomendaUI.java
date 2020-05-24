package eapli.base.app.backoffice.console.presentation.gestaochaofabrica.ordemproducao;

import eapli.base.app.common.console.presentation.interaction.UserInteractionFlow;
import eapli.base.gestaoproducao.ordemProducao.application.ConsultarOrdensProducaoController;
import eapli.base.gestaoproducao.ordemProducao.application.OrdemProducaoDTO;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

public class ConsultarOrdemProducaoByEncomendaUI extends AbstractUI {
    ConsultarOrdensProducaoController controller = new ConsultarOrdensProducaoController();

    private final String SEPARADOR = "--------------------------------------------------------------";
    @Override
    protected boolean doShow() {
        final String idEncomenda = Console.readNonEmptyLine("Insira o identificador da encomenda",
                "O identificador da encomenda não pode ser vazio");

        OrdemProducaoDTO ordem = controller.getOrdemProducaoPorEncomenda(idEncomenda);

        if(ordem != null){
            System.out.println("Encontrado: " + ordem.information + "\n" + SEPARADOR);
        }else{
            System.out.println("Não foi encontrado nenhuma ordem de produção com esse identificador de encomenda.");
        }

        UserInteractionFlow.enterToContinue();

        return false;
    }

    @Override
    public String headline() {
        return "Consultar ordem de produção por encomenda";
    }
}
