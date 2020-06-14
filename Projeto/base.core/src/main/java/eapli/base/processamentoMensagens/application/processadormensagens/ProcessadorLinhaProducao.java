package eapli.base.processamentoMensagens.application.processadormensagens;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.EstadoProcessamentoMensagens;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

import java.util.List;

import static java.lang.Thread.sleep;

public class ProcessadorLinhaProducao implements Runnable {

    private final LinhaProducao linhaProducao;

    public ProcessadorLinhaProducao(LinhaProducao linhaProducao) {
        this.linhaProducao = linhaProducao;
    }

    @Override
    public void run() {
        ProcessadorBlocoMensagens processadorBlocoMensagens = new ProcessadorBlocoMensagens();
        MensagemRepository mensagemRepository = PersistenceContext.repositories().mensagem();
        while(true) {
            if (linhaProducao.estado() == EstadoProcessamentoMensagens.ATIVO) {
                processarProximo(mensagemRepository, processadorBlocoMensagens);
            }
            else {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {}
            }
        }
    }

    private void processarProximo(MensagemRepository mensagemRepository, ProcessadorBlocoMensagens processadorBlocoMensagens) {
        List<Mensagem> mensagensAProcessar = ProcessadorBlocoMensagens.proximoBloco(mensagemRepository, linhaProducao.identifier);
        if (mensagensAProcessar.isEmpty()){
            return;
        }
        processadorBlocoMensagens.processar(mensagensAProcessar);
    }
}
