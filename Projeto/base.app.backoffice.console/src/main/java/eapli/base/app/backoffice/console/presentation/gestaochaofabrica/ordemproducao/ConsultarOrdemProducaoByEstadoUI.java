package eapli.base.app.backoffice.console.presentation.gestaochaofabrica.ordemproducao;

import eapli.base.app.common.console.presentation.interaction.UserInteractionFlow;
import eapli.base.gestaoproducao.ordemProducao.application.ConsultarOrdensProducaoController;
import eapli.base.gestaoproducao.ordemProducao.application.OrdemProducaoDTO;
import eapli.base.gestaoproducao.ordemProducao.domain.Estado;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;
import java.util.List;

public class ConsultarOrdemProducaoByEstadoUI extends AbstractUI {

    private final String SEPARADOR = "--------------------------------------------------------------";

    ConsultarOrdensProducaoController controller = new ConsultarOrdensProducaoController();

    @Override
    protected boolean doShow() {

        int i = 1;
        Estado[] estados = Estado.values();
        for(Estado est : estados){ // Mostra todos os Estados possíveis
            System.out.println("Insira " + i + " para escolher o estado: " + est);
            i++;
        }
        int escolhido = Console.readOption(1,i-1,0);

        List<OrdemProducaoDTO> ordens = controller.getOrdensProducaoPorEstado(estados[escolhido-1]);

        for(OrdemProducaoDTO ord : ordens){
            System.out.println("Encontrado: " + ord.information + "\n" + SEPARADOR);
        }

        if(ordens.size() == 0){
            System.out.println("Não foi encontrada nenhuma ordem de produção nesse estado.");
        }

        UserInteractionFlow.enterToContinue();

        return false;
    }

    @Override
    public String headline() {
        return "Consultar ordem produção pelo estado";
    }
}
