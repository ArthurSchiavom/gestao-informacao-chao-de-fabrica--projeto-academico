package eapli.base.processamentoMensagens.application;

import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.processamentoMensagens.application.tiposMensagensNotificacao.*;

public class GerarNotificacoesDeErrosFactory {

    public ValidadorMensagem getNotificacaoDeErro(Mensagem mensagem){

        switch (mensagem.mensagemID.tipoDeMensagem) {
            case CONSUMO:
                return new MensagemConsumoNotificacao();
            case PRODUCAO:
                return new MensagemProducaoNotificacao();
            case ESTORNO:
                return new MensagemEstornoNotificacao();
            case FIM_DE_ATIVIDADE:
                return new MensagemFimDeAtividadeNotificacao();
            case INICIO_DE_ATIVIDADE:
                return new MensagemInicioDeAtividadeNotificacao();
            case PARAGEM_FORCADA:
                return new MensagemParagemNotificacao();
            case ENTREGA_DE_PRODUCAO:
                return new MensagemEntregaProducaoNotificacao();
            case RETOMA_ATIVIDADE:
                return new MensagemRetomaDeActividadeNotificacao();
            default:
                return null;
        }

    }

}

