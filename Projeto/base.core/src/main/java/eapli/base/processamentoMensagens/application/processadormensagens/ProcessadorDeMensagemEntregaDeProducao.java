package eapli.base.processamentoMensagens.application.processadormensagens;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.MateriaPrima;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.TipoDeMateriaPrima;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemEntregaDeProducao;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
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

public class ProcessadorDeMensagemEntregaDeProducao implements ProcessadorMensagem {
    private ValidacaoParametrosMensagensServico validacaoParametrosMensagensServico;
    private LinhaProducaoRepository linhaProducaoRepository;
    private ProdutoRepository produtoRepository;
    private OrdemProducaoRepository ordemProducaoRepository;
    private UsoDeMaquinaRepository usoDeMaquinaRepository;
    private MensagemRepository mensagemRepository;
    private OperacoesUsoDeMaquina operacoesUsoDeMaquina;
    public ProcessadorDeMensagemEntregaDeProducao() {
        this.ordemProducaoRepository= PersistenceContext.repositories().ordemProducao();
        this.produtoRepository=PersistenceContext.repositories().produto();
        this.usoDeMaquinaRepository=PersistenceContext.repositories().usoDeMaquina();
        this.linhaProducaoRepository= PersistenceContext.repositories().linhasProducao();
        this.validacaoParametrosMensagensServico=new ValidacaoParametrosMensagensServico();
        this.mensagemRepository=PersistenceContext.repositories().mensagem();
        this.operacoesUsoDeMaquina=new OperacoesUsoDeMaquina(validacaoParametrosMensagensServico);
    }

    @Override
    public NotificacaoErro processarMensagem(Mensagem mensagem, OrdemProducao ordemProducao) {
        ValidadorMensagem validadorMensagem= new GerarNotificacoesDeErrosFactory().getNotificacaoDeErro(mensagem);
        MensagemEntregaDeProducao mensagemEntregaDeProducao=(MensagemEntregaDeProducao)mensagem;
        CodigoInternoMaquina codigoInternoMaquina=mensagemEntregaDeProducao.mensagemID.codigoInternoMaquina;
        CodigoUnico codigoUnico=mensagemEntregaDeProducao.codigoUnico;
        UsoDeMaquina usoDeMaquina=validacaoParametrosMensagensServico.verificarExistenciaDeUsoMaquina(ordemProducao.usoDeMaquinaList,codigoInternoMaquina);
        Maquina maquina=validacaoParametrosMensagensServico.getMaquinaPorIdentificador(codigoInternoMaquina);
        LinhaProducao linhaProducao=validacaoParametrosMensagensServico.getLinhaDeProducaoPorIdentificador(maquina.getLinhaProducao());
        NotificacaoErro notificacaoErro=validadorMensagem.validarMensagem(mensagemRepository,mensagemEntregaDeProducao,validacaoParametrosMensagensServico);
        if (notificacaoErro!=null)
            return notificacaoErro;
        double quantidade=mensagemEntregaDeProducao.getQuantidadeATransferir();
        try {
            operacoesUsoDeMaquina.adicionarMovimentosDeStock(quantidade,MateriaPrima.valueOf(TipoDeMateriaPrima.PRODUTO,codigoUnico.codigoUnicoValor),usoDeMaquina.movimentoStockList,mensagemEntregaDeProducao.codigo);
        } catch (IllegalDomainValueException e) {
        }
        usoDeMaquinaRepository.save(usoDeMaquina);
        return null;
    }
}
