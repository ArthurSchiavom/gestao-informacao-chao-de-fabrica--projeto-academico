package eapli.base.processamentoMensagens.application;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomensagens.domain.*;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.ordemProducao.domain.IdentificadorOrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.processamentoMensagens.application.tiposMensagensNotificacao.CriacaoNotificacaoStrategy;
import eapli.framework.domain.repositories.TransactionalContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcessamentoDeMensagensThread implements Runnable {
    private List<Mensagem> listaDeMensagemAProcessar;
    private MensagemRepository mensagemRepository;
    private NotificacaoErroRepository notificacaoErroRepository;
    private LinhaProducaoRepository linhaProducaoRepository;
    private LinhaProducao linhaProducao;
    private final TransactionalContext transactionalContext;
    private GerarNotificacoesDeErrosFactory factory;
    private final ValidacaoParametrosMensagensServico validacaoParametrosMensagensServico;

    public ProcessamentoDeMensagensThread(List<Mensagem> listaDeMensagemAProcessar, LinhaProducao linhaProducao) {
        this.transactionalContext = PersistenceContext.repositories().newTransactionalContext();
        this.notificacaoErroRepository=PersistenceContext.repositories().notificacoesErros();
        this.mensagemRepository=PersistenceContext.repositories().mensagem();
        this.listaDeMensagemAProcessar=listaDeMensagemAProcessar;
        this.linhaProducaoRepository=PersistenceContext.repositories().linhasProducao();
        this.factory=new GerarNotificacoesDeErrosFactory();
        this.linhaProducao=linhaProducao;
        this.validacaoParametrosMensagensServico=new ValidacaoParametrosMensagensServico(PersistenceContext.repositories().produto(),PersistenceContext.repositories().ordemProducao());
    }

    @Override
    public void run() {
        boolean flag=false;
        List<NotificacaoErro> listaNotificoesDeErros=new ArrayList<>();
        List<Mensagem> listaMensagensSemErros=new ArrayList<>();
        CodigoDeposito codigoDeposito;
        Produto produto;
        Maquina maquina;
        IdentificadorOrdemProducao idOrdemProducao=null;
        OrdemProducao ordemProducao;
        Date dataEmissao;
        int quantidadeAProduzir;
        for (Mensagem mensagem:listaDeMensagemAProcessar){
            CriacaoNotificacaoStrategy strategy=factory.getNotificacaoDeErro(mensagem);
            NotificacaoErro notificacaoErro=strategy.validarMensagem(linhaProducao,linhaProducaoRepository,mensagemRepository,mensagem,validacaoParametrosMensagensServico);
            if (notificacaoErro!=null) {
                listaNotificoesDeErros.add(notificacaoErro);
                break;
            }else {
                listaMensagensSemErros.add(mensagem);
            }
        }
        if (!listaMensagensSemErros.isEmpty() && listaNotificoesDeErros.isEmpty())
            enriquecerMensagens(idOrdemProducao,listaMensagensSemErros,linhaProducao.identifier);
        if(!listaNotificoesDeErros.isEmpty())
            notificacoesDeErro(listaNotificoesDeErros);
    }


    public void enriquecerMensagens(IdentificadorOrdemProducao identificadorOrdemProducao, List<Mensagem> listaDeMensagensSemErros, IdentificadorLinhaProducao identificadorLinhaProducao) {
        transactionalContext.beginTransaction();
        for (Mensagem mensagem : listaDeMensagensSemErros){
            if (mensagem.enriquecerMensagem(identificadorOrdemProducao, identificadorLinhaProducao))
                mensagemRepository.save(mensagem);
        }
        transactionalContext.commit();
    }

    public void notificacoesDeErro(List<NotificacaoErro> listaNotificacoes){
        for (NotificacaoErro notificacaoErro:listaNotificacoes)
            notificacaoErroRepository.save(notificacaoErro);
    }





}
