package eapli.base.processamentoMensagens.application.processadormensagens;

import eapli.base.gestaoproducao.gestaomensagens.domain.*;

import javax.annotation.Nullable;

@Nullable
public class ProcessadorMensagemFactory {
    public static ProcessadorMensagem processador(Mensagem mensagem) {
        if (mensagem instanceof MensagemConsumo) {
            return new ProcessadorDeMensagemDeConsumo();
        }
        if (mensagem instanceof MensagemEstorno) {
            return new ProcessadorDeMensagemDeConsumo();
        }
        if (mensagem instanceof MensagemFimDeAtividade) {
            return new ProcessadorDeMensagemDeConsumo();
        }
        if (mensagem instanceof MensagemParagemForcada) {
            return new ProcessadorDeMensagemDeConsumo();
        }
        if (mensagem instanceof MensagemProducao) {
            return new ProcessadorDeMensagemDeConsumo();
        }
        if (mensagem instanceof MensagemRetomoDeActividade) {
            return new ProcessadorDeMensagemDeConsumo();
        }
        if (mensagem instanceof MensagemEntregaDeProducao) {
            return new ProcessadorDeMensagemDeConsumo();
        }
        if (mensagem instanceof MensagemInicioDeAtividade) {
            return new ProcessadorDeMensagemDeConsumo();
        }

        return null;
    }
}
