package eapli.base.processamentoMensagens.application.processadormensagens;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemFimDeAtividade;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.processamentoMensagens.application.GerarNotificacoesDeErrosFactory;
import eapli.base.processamentoMensagens.application.ValidacaoParametrosMensagensServico;
import eapli.base.processamentoMensagens.application.tiposMensagensNotificacao.ValidadorMensagem;

public class ProcessadorDeMensagemDeFimDeAtividade implements ProcessadorMensagem {
    private ValidacaoParametrosMensagensServico validacaoParametrosMensagensServico;
    private LinhaProducaoRepository linhaProducaoRepository;
    private ProdutoRepository produtoRepository;
    private OrdemProducaoRepository ordemProducaoRepository;
    private MensagemRepository mensagemRepository;


    public ProcessadorDeMensagemDeFimDeAtividade() {
        this.ordemProducaoRepository= PersistenceContext.repositories().ordemProducao();
        this.produtoRepository=PersistenceContext.repositories().produto();
        this.linhaProducaoRepository= PersistenceContext.repositories().linhasProducao();
        this.validacaoParametrosMensagensServico=new ValidacaoParametrosMensagensServico();
        this.mensagemRepository=PersistenceContext.repositories().mensagem();
    }

    @Override
    public NotificacaoErro processarMensagem(Mensagem mensagem, OrdemProducao ordemProducao) {
        ValidadorMensagem validadorMensagem= new GerarNotificacoesDeErrosFactory().getNotificacaoDeErro(mensagem);
        MensagemFimDeAtividade mensagemFimDeAtividade=(MensagemFimDeAtividade) mensagem;
        CodigoInternoMaquina codigoInternoMaquina=mensagemFimDeAtividade.mensagemID.codigoInternoMaquina;
        Maquina maquina=validacaoParametrosMensagensServico.getMaquinaPorIdentificador(codigoInternoMaquina);
        LinhaProducao linhaProducao=validacaoParametrosMensagensServico.getLinhaDeProducaoPorIdentificador(maquina.getLinhaProducao());
        return validadorMensagem.validarMensagem(mensagemRepository,mensagemFimDeAtividade,validacaoParametrosMensagensServico);
    }
}
