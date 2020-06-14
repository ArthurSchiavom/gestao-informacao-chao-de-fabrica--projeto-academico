package eapli.base.processamentoMensagens.application.processadormensagens;

import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemInicioDeAtividade;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.indicarUsoDeMaquina.domain.FimDeExecucao;
import eapli.base.indicarUsoDeMaquina.domain.InicioDeExecucao;
import eapli.base.indicarUsoDeMaquina.domain.UsoDeMaquina;
import eapli.base.indicarUsoDeMaquina.repositories.UsoDeMaquinaRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.processamentoMensagens.application.GerarNotificacoesDeErrosFactory;
import eapli.base.processamentoMensagens.application.ValidacaoParametrosMensagensServico;
import eapli.base.processamentoMensagens.application.tiposMensagensNotificacao.ValidadorMensagem;

import java.util.List;

public class ProcessadorDeMensagemInicioDeAtividade implements  ProcessadorMensagem {
    private ValidacaoParametrosMensagensServico validacaoParametrosMensagensServico;
    private LinhaProducaoRepository linhaProducaoRepository;
    private ProdutoRepository produtoRepository;
    private OrdemProducaoRepository ordemProducaoRepository;
    private UsoDeMaquinaRepository usoDeMaquinaRepository;
    private MensagemRepository mensagemRepository;

    public ProcessadorDeMensagemInicioDeAtividade() {
        this.ordemProducaoRepository=PersistenceContext.repositories().ordemProducao();
        this.produtoRepository=PersistenceContext.repositories().produto();
        this.usoDeMaquinaRepository=PersistenceContext.repositories().usoDeMaquina();
        this.linhaProducaoRepository= PersistenceContext.repositories().linhasProducao();
        this.validacaoParametrosMensagensServico=new ValidacaoParametrosMensagensServico();
        this.mensagemRepository=PersistenceContext.repositories().mensagem();
    }


    @Override
    public NotificacaoErro processarMensagem(Mensagem mensagem, OrdemProducao ordemProducao) {
        ValidadorMensagem validadorMensagem= new GerarNotificacoesDeErrosFactory().getNotificacaoDeErro(mensagem);
        MensagemInicioDeAtividade mensagemInicioDeAtividade=(MensagemInicioDeAtividade) mensagem;
        CodigoInternoMaquina codigoInternoMaquina=mensagemInicioDeAtividade.mensagemID.codigoInternoMaquina;
        Maquina maquina=validacaoParametrosMensagensServico.getMaquinaPorIdentificador(codigoInternoMaquina);
        LinhaProducao linhaProducao=validacaoParametrosMensagensServico.getLinhaDeProducaoPorIdentificador(maquina.getLinhaProducao());
        //Validar se é null  <-
        List<UsoDeMaquina> usoDeMaquinaList=ordemProducao.usoDeMaquinaList;
        UsoDeMaquina usoDeMaquina=verificacaoUsoDeMaquina(usoDeMaquinaList,codigoInternoMaquina,ordemProducao);
        NotificacaoErro notificacaoErro=validadorMensagem.validarMensagem(linhaProducao, linhaProducaoRepository, mensagemRepository,mensagemInicioDeAtividade,validacaoParametrosMensagensServico);
        if (notificacaoErro!=null)
            return notificacaoErro;
        usoDeMaquinaRepository.save(usoDeMaquina); //So guarda se nao tiver notificacao de erro
        return null;
    }

    private UsoDeMaquina verificacaoUsoDeMaquina(List<UsoDeMaquina> lista,CodigoInternoMaquina codigoInternoMaquina,OrdemProducao ordemProducao){
        UsoDeMaquina usoDeMaquina;
        usoDeMaquina=validacaoParametrosMensagensServico.verificarExistenciaDeUsoMaquina(lista,codigoInternoMaquina);
        if (usoDeMaquina!=null) {return usoDeMaquina;}
        usoDeMaquina=new UsoDeMaquina(new InicioDeExecucao(ordemProducao.inicioExecucao),new FimDeExecucao(ordemProducao.fimExecucao),codigoInternoMaquina);
        lista.add(usoDeMaquina);
        return usoDeMaquina;
    }
}
