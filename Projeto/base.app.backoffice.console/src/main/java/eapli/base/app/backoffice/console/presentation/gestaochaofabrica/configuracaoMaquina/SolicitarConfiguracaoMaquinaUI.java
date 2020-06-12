package eapli.base.app.backoffice.console.presentation.gestaochaofabrica.configuracaoMaquina;

import eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaquina.especificacao.EspecificarFicheiroConfiguracaoController;
import eapli.base.app.common.console.presentation.formatter.ConsoleTables;
import eapli.base.app.common.console.presentation.interaction.UserInteractionFlow;
import eapli.base.gestaoproducao.gestaomaquina.aplication.dto.MaquinaDTO;
import eapli.base.solicitarConfiguracaoMaquina.application.SolicitarConfiguracaoMaquinaController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.List;

public class SolicitarConfiguracaoMaquinaUI extends AbstractUI {

    SolicitarConfiguracaoMaquinaController cont =
            new SolicitarConfiguracaoMaquinaController();

    @Override
    protected boolean doShow() {

        //-----------------------------------------------
        EspecificarFicheiroConfiguracaoController controller = new EspecificarFicheiroConfiguracaoController();
        List<MaquinaDTO> listaDeMaquinasDTO = controller.maquinas();
        if (listaDeMaquinasDTO.isEmpty()) {
            System.out.println("Não há existe maquina");
            UserInteractionFlow.enterParaContinuar();
            return false;
        }

        String maquinasSemFicheiroDeConfiguracaoDisplay = ConsoleTables.tabelaDeMaquinas(listaDeMaquinasDTO);
        System.out.println(maquinasSemFicheiroDeConfiguracaoDisplay + "\n\n");

        String idMaquina = null;
        boolean continuar = true;
        while (continuar) {
            idMaquina = Console.readNonEmptyLine("Indique o código interno da maquina: ", "Indique um código interno valido.");
            continuar = !controller.selecionarMaquina(idMaquina);
            if (continuar) {
                System.out.println("O código indicado não se encontra registado a uma maquina.\n");
            }
        }

        String caminho = null;
        int configEscolhido = 0;
        if(!cont.temConfi(idMaquina)){ // se a maquina dada nao tem ficheiro config pede um
            caminho = Console.readLine("Introduza o caminho do ficheiro de configuração a carregar: ");
        }else{
            List<String> configs = cont.ficheirosConfig(idMaquina);
            if(configs == null || configs.size() == 0){
                System.out.println("Ocorreu um erro durante a operação");
                return false;
            }

            System.out.println("Escolha 1: ");
            int i = 1;
            for(String s : configs){
                System.out.println(i + " " + s);
                i++;
            }
            configEscolhido = Console.readOption(1,configs.size(),0);

            if(configEscolhido == 0){
                return false;
            }
            configEscolhido--;
        }

        //-------------------------------------------------
//        String codigoInterno = Console.readLine("Insira o código interno da máquina: ");
        try {
            boolean sucesso = cont.enviarConfigPorTcp(caminho, idMaquina,configEscolhido);

            if (sucesso) {
                System.out.println("Operação bem sucedida");
            } else {
                System.out.println("Ocorreu um erro");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    @Override
    public String headline() {
        return "Configuração máquina";
    }
}
