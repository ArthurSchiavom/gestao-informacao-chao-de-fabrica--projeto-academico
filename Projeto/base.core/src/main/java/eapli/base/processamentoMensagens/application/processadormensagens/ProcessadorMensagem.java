package eapli.base.processamentoMensagens.application.processadormensagens;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.processamentoMensagens.application.tiposMensagensNotificacao.ValidadorMensagem;

public interface ProcessadorMensagem {
        NotificacaoErro processarMensagem(Mensagem mensagem, OrdemProducao ordemProducao, ValidadorMensagem validadorMensagem);
}
