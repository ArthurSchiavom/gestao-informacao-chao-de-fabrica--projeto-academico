package eapli.base.processamentoMensagens.application;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.*;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.TransactionalContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProcessamentoDeMensagensThread implements Runnable {
    private List<Mensagem> listaDeMensagemAProcessar;
    private MensagemRepository mensagemRepository;
    private MaquinaRepository maquinaRepository;
    private ProdutoRepository produtoRepository;
    private NotificacaoErroRepository notificacaoErroRepository;
    private LinhaProducaoRepository linhaProducaoRepository;
    private LinhaProducao linhaProducao;
    private final TransactionalContext transactionalContext;

    public ProcessamentoDeMensagensThread(List<Mensagem> listaDeMensagemAProcessar, LinhaProducao linhaProducao, MensagemRepository mensagemRepository, LinhaProducaoRepository producaoRepository) {
        this.transactionalContext = PersistenceContext.repositories().newTransactionalContext();
        this.produtoRepository= PersistenceContext.repositories().produto();
        this.maquinaRepository=PersistenceContext.repositories().maquinas();
        this.notificacaoErroRepository=PersistenceContext.repositories().notificacoesErros();
        this.listaDeMensagemAProcessar=listaDeMensagemAProcessar;
        this.mensagemRepository=mensagemRepository;
        this.linhaProducao=linhaProducao;
    }

    @Override
    public void run() {
        List<NotificacaoErro> listaNotificoesDeErros=new ArrayList<>();
        CodigoDeposito codigoDeposito;
        Optional<Maquina> mp;
        OrdemProducao ordemProducao=null;
        for (Mensagem mensagem:listaDeMensagemAProcessar){
            switch (mensagem.tipo) {
                case CONSUMO:
                    MensagemConsumo mensagemConsumo=(MensagemConsumo)mensagem;
                    codigoDeposito=mensagemConsumo.codigo;
                    if (codigoDeposito!=null){
                        if (codigoDeposito.codigo.indexOf(" ")>0 ||codigoDeposito.codigo.trim().length()==0)
                            listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemConsumo.identity(),linhaProducaoRepository,mensagemRepository));
                    }
                    //NAO HA ELEMENTOS PARA VERIFICAR POIS NENHUM É OBRIGATORIO
                    break;
                case PRODUCAO:
                    MensagemProducao mensagemProducao=(MensagemProducao)mensagem;
                    //Nada a validar nem no codigo unico nem no identificador de lote
                    //FALTA VERIFICAR LOTE ESTE NAO EXISTE
                    if (!(produtoRepository.produtoDeCodigoUnico(mensagemProducao.codigoUnico.codigoUnicoValor).isPresent()))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.ELEMENTOS_INEXISTENTES,mensagemProducao.identity(),linhaProducaoRepository,mensagemRepository));

                    break;
                case ESTORNO:
                    MensagemEstorno mensagemEstorno=(MensagemEstorno) mensagem;
                    if (!(produtoRepository.produtoDeCodigoUnico(mensagemEstorno.codigoUnico.codigoUnicoValor).isPresent()))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.ELEMENTOS_INEXISTENTES,mensagemEstorno.identity(),linhaProducaoRepository,mensagemRepository));
                    codigoDeposito=mensagemEstorno.codigo;
                    if (codigoDeposito!=null){
                        if (codigoDeposito.codigo.indexOf(" ")>0 ||codigoDeposito.codigo.trim().length()==0)
                            listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemEstorno.identity(),linhaProducaoRepository,mensagemRepository));
                    }
                    break;

                case FIM_DE_ATIVIDADE:
                    MensagemFimDeAtividade mensagemFimDeAtividade=(MensagemFimDeAtividade) mensagem;
                    if(mensagemFimDeAtividade.getOrdemProducao()!=null)
                        ordemProducao=mensagemFimDeAtividade.getOrdemProducao();
                    //NADA A VALIDAR NEM O CODIGO UNICO
                    //NAO HA ELEMENTOS PARA VERIFICAR POIS NENHUM É OBRIGATORIO
                    break;
                case INICIO_DE_ATIVIDADE:
                    MensagemInicioDeAtividade mensagemInicioDeAtividade=(MensagemInicioDeAtividade) mensagem;
                    if(mensagemInicioDeAtividade.getOrdemProducao()!=null)
                        ordemProducao=mensagemInicioDeAtividade.getOrdemProducao();
                    //NADA A VALIDAR NEM O CODIGO UNICO
                    //NAO HA ELEMENTOS PARA VERIFICAR POIS NENHUM É OBRIGATORIO
                    break;
                case PARAGEM_FORCADA:
                    MensagemParagemForcada mensagemParagemForcada=(MensagemParagemForcada) mensagem;
                    //NADA A VALIDAR NEM O CODIGO UNICO
                    //NAO HA ELEMENTOS PARA VERIFICAR POIS NENHUM É OBRIGATORIO
                    break;
                case ENTREGA_DE_PRODUCAO:
                    //VALIDACAO
                    MensagemEntregaDeProducao mensagemEntregaDeProducao=(MensagemEntregaDeProducao)mensagem;
                    codigoDeposito=mensagemEntregaDeProducao.codigo;
                    if (codigoDeposito!=null){
                        if (codigoDeposito.codigo.indexOf(" ")>0 ||codigoDeposito.codigo.trim().length()==0)
                            listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemEntregaDeProducao.identity(),linhaProducaoRepository,mensagemRepository));
                    }
                case RETOMA_ATIVIDADE:
                    //VALIDACAO
                    MensagemRetomoDeActividade mensagemRetomoDeActividade=(MensagemRetomoDeActividade) mensagem;
                    break;

            }
            if (ordemProducao!=null){
                enriquecerMensagens(ordemProducao,mensagem,linhaProducao);
            }
        }
        notificacoesDeErro(listaNotificoesDeErros);

    }


    public void enriquecerMensagens(OrdemProducao ordemProducao, Mensagem mensagem,LinhaProducao linhaProducao){
        transactionalContext.beginTransaction();
        mensagem.setLinhaProducao(linhaProducao);
        mensagem.setOrdemProducao(ordemProducao);
        mensagem.setEstadoProcessamento(EstadoProcessamento.PROCESSADO);
        mensagemRepository.save(mensagem);
        transactionalContext.commit();
    }

    public void notificacoesDeErro(List<NotificacaoErro> listaNotificacoes){
        for (NotificacaoErro notificacaoErro:listaNotificacoes)
            notificacaoErroRepository.save(notificacaoErro);
    }
}
