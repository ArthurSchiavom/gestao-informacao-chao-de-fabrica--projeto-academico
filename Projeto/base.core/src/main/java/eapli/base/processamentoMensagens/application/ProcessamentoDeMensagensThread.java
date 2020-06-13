package eapli.base.processamentoMensagens.application;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.MateriaPrima;
import eapli.base.gestaoproducao.gestaomensagens.domain.*;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.indicarUsoDeMaquina.repositories.UsoDeMaquinaRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.processamentoMensagens.application.tiposMensagensNotificacao.ValidadorMensagem;

import java.util.*;
import java.util.List;

public class ProcessamentoDeMensagensThread implements Runnable {
    private List<Mensagem> listaDeMensagemAProcessar;
    private MensagemRepository mensagemRepository;
    private ProdutoRepository produtoRepository;
    private NotificacaoErroRepository notificacaoErroRepository;
    private OrdemProducaoRepository ordemProducaoRepository;
    private UsoDeMaquinaRepository usoDeMaquinaRepository;
    private LinhaProducaoRepository linhaProducaoRepository;
    private GerarNotificacoesDeErrosFactory gerarNotificacoesDeErroFactory;
    private ProcessamentoDeMensagensFactory processamentoDeMensagensFactory;

    public ProcessamentoDeMensagensThread(List<Mensagem> listaDeMensagemAProcessar, LinhaProducao linhaProducao) {
        this.usoDeMaquinaRepository=PersistenceContext.repositories().usoDeMaquina();
        this.notificacaoErroRepository=PersistenceContext.repositories().notificacoesErros();
        this.mensagemRepository=PersistenceContext.repositories().mensagem();
        this.ordemProducaoRepository=PersistenceContext.repositories().ordemProducao();
        this.produtoRepository=PersistenceContext.repositories().produto();
        this.listaDeMensagemAProcessar=listaDeMensagemAProcessar;
        this.linhaProducaoRepository=PersistenceContext.repositories().linhasProducao();
        this.gerarNotificacoesDeErroFactory =new GerarNotificacoesDeErrosFactory();
    }

    @Override
    public void run() {
        boolean flag=false;
        NotificacaoErro notificacaoErro = null;
        List<NotificacaoErro> listaNotificoesDeErros=new ArrayList<>();
        List<Mensagem> listaMensagensSemErros=new ArrayList<>();
        CodigoDeposito codigoDeposito;
        MateriaPrima materiaPrima=null;
        OrdemProducao ordemProducao = null; //necessario por o resto
        for (Mensagem mensagem:listaDeMensagemAProcessar){
            notificacaoErro= processamentoDeMensagensFactory.getProcessamentoDeMensagens(mensagem).processarMensagem(mensagem,ordemProducao);
            if (notificacaoErro==null)
                listaMensagensSemErros.add(mensagem);
            else
                break;
        }
        if (notificacaoErro!=null)
            notificacaoErroRepository.save(notificacaoErro);
        marcarComoProcessada(listaMensagensSemErros,ordemProducao);

    }

    /**
     * Marca as mensagens que foram validadas e enriquecidas como processadas
     * @param listaDeMensagensSemErros Lista de Mensagens validadas
     */
    private void marcarComoProcessada( List<Mensagem> listaDeMensagensSemErros,OrdemProducao ordemProducao) {
        for (Mensagem mensagem : listaDeMensagensSemErros){
            mensagem.setIdentificadorOrdemDeProducao(ordemProducao.identificador);
            mensagem.setEstadoProcessamento(EstadoProcessamento.PROCESSADO);
            mensagemRepository.save(mensagem);
        }
    }





}
