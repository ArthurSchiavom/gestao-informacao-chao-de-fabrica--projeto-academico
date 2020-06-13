package eapli.base.processamentoMensagens.application;

import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.processamentoMensagens.application.processadormensagens.*;

public class ProcessamentoDeMensagensFactory {

    public ProcessadorMensagem getProcessamentoDeMensagens(Mensagem mensagem){
        switch (mensagem.mensagemID.tipoDeMensagem) {
            case CONSUMO:
                return new ProcessadorDeMensagemDeConsumo();
            case PRODUCAO:
                return new ProcessadorDeMensagemDeProducao();
            case ESTORNO:
                return new ProcessadorDeMensagemDeEstorno();
            case FIM_DE_ATIVIDADE:
                return new ProcessadorDeMensagemDeFimDeAtividade();
            case INICIO_DE_ATIVIDADE:
                return new ProcessadorDeMensagemInicioDeAtividade();
            case PARAGEM_FORCADA:
                return new ProcessadorDeMensagemDeParagem();
            case ENTREGA_DE_PRODUCAO:
                return new ProcessadorDeMensagemEntregaDeProducao();
            case RETOMA_ATIVIDADE:
                return new ProcessadorDeMensagemDeRetomaDeAtividade();
            default:
                return null;
        }

    }
}
