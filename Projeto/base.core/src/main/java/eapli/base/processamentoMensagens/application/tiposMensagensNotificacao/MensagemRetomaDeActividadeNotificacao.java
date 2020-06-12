package eapli.base.processamentoMensagens.application.tiposMensagensNotificacao;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemRetomoDeActividade;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.processamentoMensagens.application.ValidacaoParametrosMensagensServico;

import java.util.Date;

public class MensagemRetomaDeActividadeNotificacao implements CriacaoNotificacaoStrategy {
    @Override
    public NotificacaoErro validarMensagem( LinhaProducao linhaProducao, LinhaProducaoRepository linhaProducaoRepository, MensagemRepository mensagemRepository, Mensagem mensagem, ValidacaoParametrosMensagensServico validacao) {
        MensagemRetomoDeActividade mensagemRetomoDeActividade=(MensagemRetomoDeActividade) mensagem;
        Date dataEmissao=mensagemRetomoDeActividade.mensagemID.tempoEmissao.timestamp;

        TipoErroNotificacao DADOS_INVALIDOS=TipoErroNotificacao.DADOS_INVALIDOS;
        TipoErroNotificacao ELEMENTOS_INEXISTENTES=TipoErroNotificacao.ELEMENTOS_INEXISTENTES;

        //DATA
        if (!validacao.validarData(dataEmissao))
            NotificacaoErro.gerarNotificacaoDeErro(DADOS_INVALIDOS,linhaProducao,linhaProducaoRepository,mensagemRepository,mensagem);
        return null;
    }
}
