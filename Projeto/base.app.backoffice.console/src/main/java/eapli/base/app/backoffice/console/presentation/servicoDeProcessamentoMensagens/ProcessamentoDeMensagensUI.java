package eapli.base.app.backoffice.console.presentation.servicoDeProcessamentoMensagens;

import eapli.base.app.common.console.presentation.formatter.ConsoleTables;
import eapli.base.app.common.console.presentation.formatter.SimpleConsoleMessages;
import eapli.base.app.common.console.presentation.interaction.UserInteractionFlow;
import eapli.base.gestaoproducao.gestaolinhasproducao.dto.LinhaProducaoDTO;
import eapli.base.processamentoMensagens.application.DTO.AgendamentoDeProcessamentoDTO;
import eapli.base.processamentoMensagens.application.ProcessamentoDeMensagensController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ProcessamentoDeMensagensUI extends AbstractUI {
    ProcessamentoDeMensagensController controller=new ProcessamentoDeMensagensController();


    @Override
    protected boolean doShow() {
        List<LinhaProducaoDTO> lista= controller.listaDeLinhasDeProducao();
        System.out.println(lista.size());
        if (lista.isEmpty()) {
            System.out.println("Não há nenhuma linha de producao registada.\n");
            UserInteractionFlow.enterParaContinuar();
            return false;
        }
        String maquinasSemFicheiroDeConfiguracaoDisplay = ConsoleTables.tabelaDeLinhasDeProducao(lista, true, 0);
        System.out.println(maquinasSemFicheiroDeConfiguracaoDisplay + "\n\n");
        boolean continuar = true;
        try {
            listaDeEscolhas(lista);
            List<AgendamentoDeProcessamentoDTO> listaDeAgendamentos=controller.listaDeAgendamentos();
            if (!listaDeAgendamentos.isEmpty()){
                String materiaisDisplay = ConsoleTables.tabelaDeAgendamentoDeProcessamentoDeMensagens(listaDeAgendamentos);
                System.out.println(SimpleConsoleMessages.CLEAR_SCREEN + materiaisDisplay + "\n\nProssiga com o seu agendamento atendendo aos periodos registados.\n");
            }
            String dataInicio=Console.readNonEmptyLine("Insira a data de inicio de processamento (YYYY-MM-DD)","Este campo nao pode ser vazio");
            String tempoInicio=Console.readNonEmptyLine("Insira a Hora/Min de inicio de processamento (HH:MM)","Este campo nao pode ser vazio");
            String dataFinal=Console.readNonEmptyLine("Insira a data final de processamento (YYYY-MM-DD)","Este campo nao pode ser vazio");
            String tempoFinal=Console.readNonEmptyLine("Insira a Hora/Min final de processamento (HH:MM)","Este campo nao pode ser vazio");

            if (!controller.validarInput(dataInicio,dataFinal,tempoInicio,tempoFinal))
                return false;
            if (controller.verificarDisponibilidade()) {
                controller.registar();
                System.out.println("Efetuado agendamento com sucesso!");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e){
            System.out.println("Erro: "+ e.getMessage());
        }
        return continuar;
    }

    private void listaDeEscolhas(List<LinhaProducaoDTO> lista){
        int quantidade,contador=0,idLinhaProducao=-1;
        List<String> ids=new ArrayList<>();
        quantidade=Console.readInteger("Pretende efetuar o processamento para quantas linhas de producao?");
        while (contador<quantidade) {
            idLinhaProducao = Console.readInteger("Insira o numero associado á linha de producao que pretende processar as mensagens: ");
            if (idLinhaProducao < 0 || idLinhaProducao >= lista.size()) {
                System.out.println("ID invalido! Insira novamente");
            }else {
                if (ids.contains(idLinhaProducao))
                    System.out.println("ID ja inserido na lista");
                else {
                    ids.add(lista.get(idLinhaProducao).identificadorLinhaProducao);
                    contador++;
                }
            }
        }
        controller.selecionarLinhaProducao(ids);
    }

    @Override
    public String headline() {
        return "Processamento de mensagens disponiveis no sistema";
    }
}
