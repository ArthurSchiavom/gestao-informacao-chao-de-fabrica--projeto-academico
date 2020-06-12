package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaquina.especificacao;

import eapli.base.app.common.console.presentation.formatter.ConsoleTables;
import eapli.base.app.common.console.presentation.interaction.UserInteractionFlow;
import eapli.base.gestaoproducao.gestaomaquina.aplication.dto.MaquinaDTO;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.io.IOException;
import java.util.List;

public class EspecificarFicheiroConfiguracaoUI extends AbstractUI {
    @Override
    protected boolean doShow() {
        EspecificarFicheiroConfiguracaoController controller = new EspecificarFicheiroConfiguracaoController();
        List<MaquinaDTO> listaDeMaquinasDTO = controller.maquinas();
        if (listaDeMaquinasDTO.isEmpty()) {
            System.out.println("Não há nenhuma maquina");
            UserInteractionFlow.enterParaContinuar();
            return false;
        }

        String maquinasSemFicheiroDeConfiguracaoDisplay = ConsoleTables.tabelaDeMaquinas(listaDeMaquinasDTO);
        System.out.println(maquinasSemFicheiroDeConfiguracaoDisplay + "\n\n");

        String idMaquina ;
        boolean continuar = true;
        while (continuar) {
            idMaquina = Console.readNonEmptyLine("Indique o código interno da maquina onde pretende associar um ficheiro de configuracao.", "Indique um código interno valido.");
            continuar = !controller.selecionarMaquina(idMaquina);
            if (continuar) {
                System.out.println("O código indicado não se encontra registado a uma maquina.\n");
            }
        }
        System.out.println("Registo da ficheiro de configuracao em progresso...\n");
        try {
            String pathFicheiro= Console.readNonEmptyLine("Insira o caminhi do ficheiro: ","O caminho do ficheiro nao pode ser vazio!");
            String descricao=Console.readNonEmptyLine("Insira a descricao do ficheiro: ","Este campo nao pode ser vazio!");
            controller.registar(descricao,pathFicheiro);
            System.out.println("Ficha de produção associada com sucesso!\n");
        } catch (IOException|IllegalArgumentException e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }
        UserInteractionFlow.enterParaContinuar();
        return false;
    }

    @Override
    public String headline() {
        return null;
    }
}
