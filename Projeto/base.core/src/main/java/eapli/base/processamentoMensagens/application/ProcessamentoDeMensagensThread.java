package eapli.base.processamentoMensagens.application;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.TipoErroNotificacao;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.base.gestaoproducao.gestaomensagens.domain.*;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.Identificador;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.TransactionalContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ProcessamentoDeMensagensThread implements Runnable {
    private List<Mensagem> listaDeMensagemAProcessar;
    private MensagemRepository mensagemRepository;
    private OrdemProducaoRepository ordemProducaoRepository;
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
        this.ordemProducaoRepository= PersistenceContext.repositories().ordemProducao();
    }

    @Override
    public void run() {
        List<NotificacaoErro> listaNotificoesDeErros=new ArrayList<>();
        CodigoDeposito codigoDeposito;
        Produto produto;
        Maquina maquina;
        Identificador idOrdemProducao=null;
        OrdemProducao ordemProducao;
        Date dataEmissao;
        int quantidadeAProduzir;
        for (Mensagem mensagem:listaDeMensagemAProcessar){
            switch (mensagem.mensagemID.tipoDeMensagem) {
                case CONSUMO:
                    MensagemConsumo mensagemConsumo=(MensagemConsumo)mensagem;
                    codigoDeposito=mensagemConsumo.codigo;
                    quantidadeAProduzir=mensagemConsumo.getQuantidadeProduzir();
                    dataEmissao=mensagemConsumo.mensagemID.tempoEmissao.timestamp;
                    maquina=getMaquinaPorIdentificador(mensagemConsumo.mensagemID.codigoInternoMaquina);
                    produto =getProdutoPorIdentificador(mensagemConsumo.codigoUnico);
                    //DATA
                    if (!validarData(dataEmissao))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemConsumo.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO INTERNO MAQUINA
                    if (maquina==null)
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier,TipoErroNotificacao.ELEMENTOS_INEXISTENTES,mensagemConsumo.identity(),linhaProducaoRepository,mensagemRepository));
                    //QUANTIDADE A PRODUZIR
                    if (!validarQuantidade(quantidadeAProduzir))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier,TipoErroNotificacao.DADOS_INVALIDOS,mensagemConsumo.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO UNICO
                    if (produto==null)
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier,TipoErroNotificacao.ELEMENTOS_INEXISTENTES,mensagemConsumo.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO DEPOSITO
                    if (codigoDeposito!=null){
                        if(!validarCodigoDeDeposito(codigoDeposito))
                            listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemConsumo.identity(),linhaProducaoRepository,mensagemRepository));
                    }
                    break;
                case PRODUCAO:
                    MensagemProducao mensagemProducao=(MensagemProducao)mensagem;
                    dataEmissao=mensagemProducao.mensagemID.tempoEmissao.timestamp;
                    maquina=getMaquinaPorIdentificador(mensagemProducao.mensagemID.codigoInternoMaquina);
                    quantidadeAProduzir=mensagemProducao.getQuantidade();
                    produto =getProdutoPorIdentificador(mensagemProducao.codigoUnico);
                    //DATA
                    if (!validarData(dataEmissao))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemProducao.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO INTERNO MAQUINA
                    if (maquina==null)
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier,TipoErroNotificacao.ELEMENTOS_INEXISTENTES,mensagemProducao.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO UNICO
                    if (produto==null)
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.ELEMENTOS_INEXISTENTES,mensagemProducao.identity(),linhaProducaoRepository,mensagemRepository));
                    //QUANTIDADE A PRODUZIR
                    if (!validarQuantidade(quantidadeAProduzir))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier,TipoErroNotificacao.DADOS_INVALIDOS,mensagemProducao.identity(),linhaProducaoRepository,mensagemRepository));
                    //Lote
                    break;
                case ESTORNO:
                    MensagemEstorno mensagemEstorno=(MensagemEstorno) mensagem;
                    quantidadeAProduzir=mensagemEstorno.getQuantidadeProduzir();
                    produto=getProdutoPorCodigoUnico(mensagemEstorno.codigoUnico);
                    codigoDeposito=mensagemEstorno.codigo;
                    dataEmissao=mensagemEstorno.mensagemID.tempoEmissao.timestamp;
                    maquina=getMaquinaPorIdentificador(mensagemEstorno.mensagemID.codigoInternoMaquina);
                    //DATA
                    if (!validarData(dataEmissao))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemEstorno.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO INTERNO MAQUINA
                    if (maquina==null)
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier,TipoErroNotificacao.ELEMENTOS_INEXISTENTES,mensagemEstorno.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO UNICO
                    if (produto==null)
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.ELEMENTOS_INEXISTENTES,mensagemEstorno.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO DEPOSITO
                    if (codigoDeposito!=null){
                        if(validarCodigoDeDeposito(codigoDeposito))
                            listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemEstorno.identity(),linhaProducaoRepository,mensagemRepository));
                    }
                    //QUANTIDADE A PRODUZIR
                    if (!validarQuantidade(quantidadeAProduzir))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier,TipoErroNotificacao.DADOS_INVALIDOS,mensagemEstorno.identity(),linhaProducaoRepository,mensagemRepository));
                    break;

                case FIM_DE_ATIVIDADE:
                    MensagemFimDeAtividade mensagemFimDeAtividade=(MensagemFimDeAtividade) mensagem;
                    dataEmissao=mensagemFimDeAtividade.mensagemID.tempoEmissao.timestamp;
                    maquina=getMaquinaPorIdentificador(mensagemFimDeAtividade.mensagemID.codigoInternoMaquina);
                    //DATA
                    if (!validarData(dataEmissao))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemFimDeAtividade.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO INTERNO MAQUINA
                    if (maquina==null)
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier,TipoErroNotificacao.ELEMENTOS_INEXISTENTES,mensagemFimDeAtividade.identity(),linhaProducaoRepository,mensagemRepository));
                    //IDENTIFICADOR DE ORDEM DE PRODUCAO
                    idOrdemProducao=mensagemFimDeAtividade.getIdentificadorOrdemDeProducao();
                    if (idOrdemProducao!=null) {
                        ordemProducao = getOrdemDeProducaoPorIdentificador(idOrdemProducao);
                        if (ordemProducao == null)
                            listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.ELEMENTOS_INEXISTENTES, mensagemFimDeAtividade.identity(), linhaProducaoRepository, mensagemRepository));
                    }
                    break;
                case INICIO_DE_ATIVIDADE:
                    MensagemInicioDeAtividade mensagemInicioDeAtividade=(MensagemInicioDeAtividade) mensagem;
                    dataEmissao=mensagemInicioDeAtividade.mensagemID.tempoEmissao.timestamp;
                    maquina=getMaquinaPorIdentificador(mensagemInicioDeAtividade.mensagemID.codigoInternoMaquina);
                    //DATA
                    if (!validarData(dataEmissao))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemInicioDeAtividade.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO INTERNO MAQUINA
                    if (maquina==null)
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier,TipoErroNotificacao.ELEMENTOS_INEXISTENTES,mensagemInicioDeAtividade.identity(),linhaProducaoRepository,mensagemRepository));
                    //IDENTIFICADOR DE ORDEM DE PRODUCAO
                    idOrdemProducao=mensagemInicioDeAtividade.getIdentificadorOrdemDeProducao();
                    if (idOrdemProducao!=null) {
                        ordemProducao = getOrdemDeProducaoPorIdentificador(idOrdemProducao);
                        if (ordemProducao == null)
                            listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.ELEMENTOS_INEXISTENTES, mensagemInicioDeAtividade.identity(), linhaProducaoRepository, mensagemRepository));
                    }
                    break;
                case PARAGEM_FORCADA:
                    MensagemParagemForcada mensagemParagemForcada=(MensagemParagemForcada) mensagem;
                    dataEmissao=mensagemParagemForcada.mensagemID.tempoEmissao.timestamp;
                    maquina=getMaquinaPorIdentificador(mensagemParagemForcada.mensagemID.codigoInternoMaquina);
                    //DATA
                    if (!validarData(dataEmissao))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemParagemForcada.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO INTERNO MAQUINA
                    if (maquina==null)
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier,TipoErroNotificacao.ELEMENTOS_INEXISTENTES,mensagemParagemForcada.identity(),linhaProducaoRepository,mensagemRepository));
                    break;
                case ENTREGA_DE_PRODUCAO:
                    MensagemEntregaDeProducao mensagemEntregaDeProducao=(MensagemEntregaDeProducao)mensagem;
                    quantidadeAProduzir=mensagemEntregaDeProducao.getQuantidadeProduzir();
                    dataEmissao=mensagemEntregaDeProducao.mensagemID.tempoEmissao.timestamp;
                    maquina=getMaquinaPorIdentificador(mensagemEntregaDeProducao.mensagemID.codigoInternoMaquina);
                    codigoDeposito=mensagemEntregaDeProducao.codigo;
                    //DATA
                    if (!validarData(dataEmissao))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemEntregaDeProducao.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO INTERNO MAQUINA
                    if (maquina==null)
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier,TipoErroNotificacao.ELEMENTOS_INEXISTENTES,mensagemEntregaDeProducao.identity(),linhaProducaoRepository,mensagemRepository));
                    //QUANTIDADE A PRODUZIR
                    if (!validarQuantidade(quantidadeAProduzir))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier,TipoErroNotificacao.DADOS_INVALIDOS,mensagemEntregaDeProducao.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO DEPOSITO
                    if (codigoDeposito!=null){
                        if(validarCodigoDeDeposito(codigoDeposito))
                            listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemEntregaDeProducao.identity(),linhaProducaoRepository,mensagemRepository));
                    }
                    break;
                case RETOMA_ATIVIDADE:
                    MensagemRetomoDeActividade mensagemRetomoDeActividade=(MensagemRetomoDeActividade) mensagem;
                    dataEmissao=mensagemRetomoDeActividade.mensagemID.tempoEmissao.timestamp;
                    maquina=getMaquinaPorIdentificador(mensagemRetomoDeActividade.mensagemID.codigoInternoMaquina);
                    //DATA
                    if (!validarData(dataEmissao))
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier, TipoErroNotificacao.DADOS_INVALIDOS,mensagemRetomoDeActividade.identity(),linhaProducaoRepository,mensagemRepository));
                    //CODIGO INTERNO MAQUINA
                    if (maquina==null)
                        listaNotificoesDeErros.add(new NotificacaoErro(linhaProducao.identifier,TipoErroNotificacao.ELEMENTOS_INEXISTENTES,mensagemRetomoDeActividade.identity(),linhaProducaoRepository,mensagemRepository));
                    break;
            }
            if (idOrdemProducao!=null){
                enriquecerMensagens(idOrdemProducao,mensagem,linhaProducao);
            }
        }
        notificacoesDeErro(listaNotificoesDeErros);
    }


    public void enriquecerMensagens(Identificador identificadorOrdemProducao, Mensagem mensagem,LinhaProducao linhaProducao){
        transactionalContext.beginTransaction();
        mensagem.setLinhaProducao(linhaProducao);
        mensagem.setIdentificadorOrdemDeProducao(identificadorOrdemProducao);
        mensagem.setEstadoProcessamento(EstadoProcessamento.PROCESSADO);
        mensagemRepository.save(mensagem);
        transactionalContext.commit();
    }

    public void notificacoesDeErro(List<NotificacaoErro> listaNotificacoes){
        for (NotificacaoErro notificacaoErro:listaNotificacoes)
            notificacaoErroRepository.save(notificacaoErro);
    }

    private boolean validarData(Date date){
        if (date.compareTo(new Date())>0)
            return false;
        return true;
    }

    private boolean validarCodigoDeDeposito(CodigoDeposito codigoDeposito){
        if (codigoDeposito.codigo.indexOf(" ")>0 ||codigoDeposito.codigo.trim().length()==0)
            return false;
        return true;
    }

    private boolean validarQuantidade(int quantidade){
        return quantidade>0;
    }

    private Produto getProdutoPorCodigoUnico(CodigoUnico codigoUnico){
        Optional<Produto> produto;
        produto=produtoRepository.produtoDeCodigoUnico(codigoUnico.codigoUnicoValor);
        if (!produto.isPresent())
            return null;
        return produto.get();
    }

    private OrdemProducao getOrdemDeProducaoPorIdentificador(Identificador identificador){
        Optional<OrdemProducao> ordemProducao;
        ordemProducao=ordemProducaoRepository.findByIdentifier(identificador);
        if (!ordemProducao.isPresent())
            return null;
        return ordemProducao.get();
    }

    private Produto getProdutoPorIdentificador(CodigoUnico codigoUnico){
        Optional<Produto> produto;
        produto=produtoRepository.produtoDeCodigoUnico(codigoUnico.codigoUnicoValor.trim());
        if (!produto.isPresent())
            return null;
        return produto.get();
    }

    private Maquina getMaquinaPorIdentificador(CodigoInternoMaquina codigoInternoMaquina){
        Optional<Maquina> maquina;
        maquina=maquinaRepository.findByIdentifier(codigoInternoMaquina);
        if (!maquina.isPresent())
            return null;
        return maquina.get();
    }
}
