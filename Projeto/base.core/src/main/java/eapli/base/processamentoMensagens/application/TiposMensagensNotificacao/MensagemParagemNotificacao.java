package eapli.base.processamentoMensagens.application.TiposMensagensNotificacao;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemParagemForcada;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.processamentoMensagens.application.ValidacaoParametrosMensagensServico;

import java.util.Date;

public class MensagemParagemNotificacao implements CriacaoNotificacaoStrategy {

    @Override
    public NotificacaoErro validarMensagem( LinhaProducao linhaProducao, LinhaProducaoRepository linhaProducaoRepository, MensagemRepository mensagemRepository, Mensagem mensagem, ValidacaoParametrosMensagensServico validacao) {
        MensagemParagemForcada mensagemParagemForcada=(MensagemParagemForcada) mensagem;
        Date dataEmissao=mensagemParagemForcada.mensagemID.tempoEmissao.timestamp;

        TipoErroNotificacao DADOS_INVALIDOS=TipoErroNotificacao.DADOS_INVALIDOS;
        TipoErroNotificacao ELEMENTOS_INEXISTENTES=TipoErroNotificacao.ELEMENTOS_INEXISTENTES;

        //DATA
        if (!validacao.validarData(dataEmissao))
            return  NotificacaoErro.gerarNotificacaoDeErro(DADOS_INVALIDOS,linhaProducao,linhaProducaoRepository,mensagemRepository,mensagem);
        return null;
    }
}
