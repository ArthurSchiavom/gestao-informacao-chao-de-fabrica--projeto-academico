package eapli.base.processamentoMensagens.application.processadormensagens;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemRetomoDeActividade;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.indicarUsoDeMaquina.domain.RetomaExecucao;
import eapli.base.indicarUsoDeMaquina.domain.UsoDeMaquina;
import eapli.base.indicarUsoDeMaquina.repositories.UsoDeMaquinaRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.processamentoMensagens.application.ValidacaoParametrosMensagensServico;
import eapli.base.processamentoMensagens.application.tiposMensagensNotificacao.ValidadorMensagem;

public class ProcessadorDeMensagemDeRetomaDeAtividade implements ProcessadorMensagem{
    private ValidacaoParametrosMensagensServico validacaoParametrosMensagensServico;
    private LinhaProducaoRepository linhaProducaoRepository;
    private ProdutoRepository produtoRepository;
    private OrdemProducaoRepository ordemProducaoRepository;
    private UsoDeMaquinaRepository usoDeMaquinaRepository;
    private MensagemRepository mensagemRepository;

    public ProcessadorDeMensagemDeRetomaDeAtividade() {
        this.ordemProducaoRepository= PersistenceContext.repositories().ordemProducao();
        this.produtoRepository=PersistenceContext.repositories().produto();
        this.usoDeMaquinaRepository=PersistenceContext.repositories().usoDeMaquina();
        this.linhaProducaoRepository= PersistenceContext.repositories().linhasProducao();
        this.validacaoParametrosMensagensServico=new ValidacaoParametrosMensagensServico();
        this.mensagemRepository=PersistenceContext.repositories().mensagem();
    }

    @Override
    public NotificacaoErro processarMensagem(Mensagem mensagem, OrdemProducao ordemProducao, ValidadorMensagem validadorMensagem) {
        MensagemRetomoDeActividade mensagemRetomoDeActividade =(MensagemRetomoDeActividade) mensagem;
        CodigoInternoMaquina codigoInternoMaquina=mensagemRetomoDeActividade.mensagemID.codigoInternoMaquina;
        Maquina maquina=validacaoParametrosMensagensServico.getMaquinaPorIdentificador(codigoInternoMaquina);
        LinhaProducao linhaProducao=validacaoParametrosMensagensServico.getLinhaDeProducaoPorIdentificador(maquina.getLinhaProducao());
        UsoDeMaquina usoDeMaquina=validacaoParametrosMensagensServico.verificarExistenciaDeUsoMaquina(ordemProducao.usoDeMaquinaList,codigoInternoMaquina);
        usoDeMaquina.retomaExecucaoList.add(new RetomaExecucao(mensagemRetomoDeActividade.mensagemID.tempoEmissao.timestamp));
        usoDeMaquinaRepository.save(usoDeMaquina);
        return null;
    }
}
