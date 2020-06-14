package eapli.base.processamentoMensagens.application.processadormensagens;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.MateriaPrima;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemEstorno;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.indicarUsoDeMaquina.domain.UsoDeMaquina;
import eapli.base.indicarUsoDeMaquina.repositories.UsoDeMaquinaRepository;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.processamentoMensagens.application.GerarNotificacoesDeErrosFactory;
import eapli.base.processamentoMensagens.application.OperacoesUsoDeMaquina;
import eapli.base.processamentoMensagens.application.ValidacaoParametrosMensagensServico;
import eapli.base.processamentoMensagens.application.tiposMensagensNotificacao.ValidadorMensagem;

public class ProcessadorDeMensagemDeEstorno implements ProcessadorMensagem {
    private ValidacaoParametrosMensagensServico validacaoParametrosMensagensServico;
    private LinhaProducaoRepository linhaProducaoRepository;
    private ProdutoRepository produtoRepository;
    private OrdemProducaoRepository ordemProducaoRepository;
    private MensagemRepository mensagemRepository;
    private UsoDeMaquinaRepository usoDeMaquinaRepository;
    private OperacoesUsoDeMaquina operacoesUsoDeMaquina;

    public ProcessadorDeMensagemDeEstorno() {
        this.ordemProducaoRepository= PersistenceContext.repositories().ordemProducao();
        this.produtoRepository=PersistenceContext.repositories().produto();
        this.linhaProducaoRepository= PersistenceContext.repositories().linhasProducao();
        this.usoDeMaquinaRepository=PersistenceContext.repositories().usoDeMaquina();
        this.validacaoParametrosMensagensServico=new ValidacaoParametrosMensagensServico();
        this.operacoesUsoDeMaquina=new OperacoesUsoDeMaquina(validacaoParametrosMensagensServico);
        this.mensagemRepository=PersistenceContext.repositories().mensagem();
    }

    @Override
    public NotificacaoErro processarMensagem(Mensagem mensagem, OrdemProducao ordemProducao) {
        ValidadorMensagem validadorMensagem= new GerarNotificacoesDeErrosFactory().getNotificacaoDeErro(mensagem);
        MensagemEstorno mensagemEstorno=(MensagemEstorno) mensagem;
        CodigoInternoMaquina codigoInternoMaquina=mensagemEstorno.mensagemID.codigoInternoMaquina;
        CodigoDeposito codigoDeposito=mensagemEstorno.codigoDeposito;
        UsoDeMaquina usoDeMaquina=validacaoParametrosMensagensServico.verificarExistenciaDeUsoMaquina(ordemProducao.usoDeMaquinaList,codigoInternoMaquina);
        Maquina maquina=validacaoParametrosMensagensServico.getMaquinaPorIdentificador(codigoInternoMaquina);
        LinhaProducao linhaProducao=validacaoParametrosMensagensServico.getLinhaDeProducaoPorIdentificador(maquina.getLinhaProducao());
        String idMateriaPrima=mensagemEstorno.idMateriaPrima;
        NotificacaoErro notificacaoErro=validadorMensagem.validarMensagem(linhaProducao, linhaProducaoRepository, mensagemRepository,mensagemEstorno,validacaoParametrosMensagensServico);
        if (notificacaoErro!=null){return notificacaoErro;}
        MateriaPrima materiaPrima=operacoesUsoDeMaquina.obterMateriaPrima(idMateriaPrima,ordemProducao);
        try {
            operacoesUsoDeMaquina.adicionarMovimentosDeStock(mensagemEstorno.getQuantidadeProduzir(),materiaPrima,usoDeMaquina.movimentoStockList,codigoDeposito);
        } catch (IllegalDomainValueException e) {
        }
        usoDeMaquinaRepository.save(usoDeMaquina);
        return null;
    }
}
