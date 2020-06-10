package eapli.base.processamentoMensagens.application.TiposMensagensNotificacao;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemInicioDeAtividade;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.IdentificadorOrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.processamentoMensagens.application.ValidacaoParametrosMensagensServico;

import javax.swing.text.Element;
import java.util.Date;

public class MensagemInicioDeAtividadeNotificacao implements  CriacaoNotificacaoStrategy{

    @Override
    public NotificacaoErro validarMensagem( LinhaProducao linhaProducao, LinhaProducaoRepository linhaProducaoRepository, MensagemRepository mensagemRepository, Mensagem mensagem, ValidacaoParametrosMensagensServico validacao) {
        MensagemInicioDeAtividade mensagemInicioDeAtividade=(eapli.base.gestaoproducao.gestaomensagens.domain.MensagemInicioDeAtividade) mensagem;
        Date dataEmissao=mensagemInicioDeAtividade.mensagemID.tempoEmissao.timestamp;
        IdentificadorOrdemProducao idOrdemProducao=mensagemInicioDeAtividade.getIdentificadorOrdemDeProducao();
        OrdemProducao ordemProducao;

        TipoErroNotificacao DADOS_INVALIDOS=TipoErroNotificacao.DADOS_INVALIDOS;
        TipoErroNotificacao ELEMENTOS_INEXISTENTES=TipoErroNotificacao.ELEMENTOS_INEXISTENTES;
        //DATA
        if (!validacao.validarData(dataEmissao))
            return NotificacaoErro.gerarNotificacaoDeErro(DADOS_INVALIDOS,linhaProducao,linhaProducaoRepository,mensagemRepository,mensagemInicioDeAtividade);
        //IDENTIFICADOR DE ORDEM DE PRODUCAO

        if (idOrdemProducao!=null) {
            ordemProducao = validacao.getOrdemDeProducaoPorIdentificador(idOrdemProducao);
            if (ordemProducao == null)
                return NotificacaoErro.gerarNotificacaoDeErro(ELEMENTOS_INEXISTENTES,linhaProducao,linhaProducaoRepository,mensagemRepository,mensagem);
        }
        return null;
    }
}
