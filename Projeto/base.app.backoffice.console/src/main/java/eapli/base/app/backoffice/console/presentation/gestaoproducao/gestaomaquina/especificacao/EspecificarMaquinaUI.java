package eapli.base.app.backoffice.console.presentation.gestaoproducao.gestaomaquina.especificacao;

import eapli.base.gestaoproducao.gestaomaquina.aplication.dto.LinhaProducaoDTO;
import eapli.base.gestaoproducao.gestaomaquina.aplication.especificacao.EspecificarMaquinaController;
import eapli.framework.presentation.console.AbstractUI;
import eapli.framework.util.Console;

import java.util.List;

/**
 * UI para registar a máquina
 */
public class EspecificarMaquinaUI extends AbstractUI {

    private final EspecificarMaquinaController theController = new EspecificarMaquinaController();

    @Override
    protected boolean doShow() {
        List<LinhaProducaoDTO> linhas = theController.getLinhasDTO();
        if (linhas.isEmpty() || linhas == null) {
            System.out.println("Não há linhas de produção válidas para a máquina");
            return false;
        }
        int i = 0;
        for (LinhaProducaoDTO linha : linhas) {
            i++;
            System.out.println("Insira: " + i + " para a linha: " + linha.identificadorLinhaProducao);
        }
        System.out.println("(Insira 0 para sair)");

        final int escolha = Console.readOption(1, i, 0);
        if (escolha != 0) {

            final int ordemLinhaProducao = Console.readInteger("Insira a posição na linha de produção");
            boolean existeMaquinaNaLinhaNaOrdem = theController.existeMaquinaEmLinhaProducaoNaOrdem(escolha,ordemLinhaProducao);

            if(existeMaquinaNaLinhaNaOrdem){
                System.out.println("Já existe uma máquina nessa linha de produção nessa posição\n" +
                        "0. Pretende cancelar a operação\n" +
                        "1. Meter esta máquina na posição "+ ordemLinhaProducao +" e mover as outras máquinas para as posições seguintes");
                        final int reescrever = Console.readOption(1, 1, 0);
                        if(reescrever == 0){
                            System.out.println("Máquina não registada");
                            // continua no mesmo menu
                            return false;
                        }

            }

            final String codigoInterno = Console.readNonEmptyLine("Insira o código interno", "O código interno não pode ser vazio");
            final String numeroSerie = Console.readNonEmptyLine("Insira o número de serie da máquina", "O número de série da máquina não pode ser vazio");
            final String descricao = Console.readLine("Descrição da máquina");
            final String marca = Console.readLine("Marca da máquina");
            final String modelo = Console.readLine("Modelo da máquina");
            final String identificadorProtocoloComunicacao = Console.readNonEmptyLine("Identificador protocolo de comunicação da máquina", "Não pode ser vazio");

            try {
                theController.registarMaquina(escolha, ordemLinhaProducao, codigoInterno, numeroSerie, descricao, marca, modelo, identificadorProtocoloComunicacao, existeMaquinaNaLinhaNaOrdem);
                System.out.println("Máquina registada com sucesso");
                return false;
            } catch (Exception ex) {
            }
        }
        System.out.println("Máquina não registada");

        // continua no mesmo menu
        return false;
    }

    @Override
    public String headline() {
        return "Registar máquina";
    }
}
