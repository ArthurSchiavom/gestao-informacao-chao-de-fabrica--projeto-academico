package eapli.base.processamentoMensagens.application;

import eapli.base.comum.domain.medicao.QuantidadePositiva;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.Repository.ProdutoProduzidoRepository;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.domain.IdentificadorDeLote;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.domain.ProdutoProduzido;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.domain.QuantidadeDeProduto;
import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.repository.NotificacaoErroRepository;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.MateriaPrima;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.QuantidadeDeMateriaPrima;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.TipoDeMateriaPrima;
import eapli.base.gestaoproducao.gestaomensagens.domain.*;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.movimentos.domain.MovimentoStock;
import eapli.base.gestaoproducao.movimentos.repositoy.MovimentoStockRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.IdentificadorOrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.indicarUsoDeMaquina.domain.*;
import eapli.base.indicarUsoDeMaquina.repositories.UsoDeMaquinaRepository;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.processamentoMensagens.application.TiposMensagensNotificacao.CriacaoNotificacaoStrategy;
import eapli.framework.domain.repositories.TransactionalContext;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ProcessamentoDeMensagensThread implements Runnable {
    private List<Mensagem> listaDeMensagemAProcessar;
    private MensagemRepository mensagemRepository;
    private ProdutoRepository produtoRepository;
    private ProdutoProduzidoRepository produtoProduzidoRepository;
    private NotificacaoErroRepository notificacaoErroRepository;
    private OrdemProducaoRepository ordemProducaoRepository;
    private MovimentoStockRepository movimentoStockRepository;
    private UsoDeMaquinaRepository usoDeMaquinaRepository;
    private LinhaProducaoRepository linhaProducaoRepository;
    private LinhaProducao linhaProducao;
    private final TransactionalContext transactionalContext;
    private GerarNotificacoesDeErrosFactory factory;
    private final ValidacaoParametrosMensagensServico validacaoParametrosMensagensServico;

    public ProcessamentoDeMensagensThread(List<Mensagem> listaDeMensagemAProcessar, LinhaProducao linhaProducao) {
        this.movimentoStockRepository=PersistenceContext.repositories().movimentoStock();
        this.usoDeMaquinaRepository=PersistenceContext.repositories().usoDeMaquina();
        this.transactionalContext = PersistenceContext.repositories().newTransactionalContext();
        this.notificacaoErroRepository=PersistenceContext.repositories().notificacoesErros();
        this.mensagemRepository=PersistenceContext.repositories().mensagem();
        this.ordemProducaoRepository=PersistenceContext.repositories().ordemProducao();
        this.produtoRepository=PersistenceContext.repositories().produto();
        this.produtoProduzidoRepository=PersistenceContext.repositories().produtoProduzido();
        this.listaDeMensagemAProcessar=listaDeMensagemAProcessar;
        this.linhaProducaoRepository=PersistenceContext.repositories().linhasProducao();
        this.factory=new GerarNotificacoesDeErrosFactory();
        this.linhaProducao=linhaProducao;
        this.validacaoParametrosMensagensServico=new ValidacaoParametrosMensagensServico(produtoRepository,ordemProducaoRepository);
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
        OrdemProducao ordemProducao=null;
        Date dataInicio=null;
        Date dataParagem=null;
        Date dataRetoma=null;
        Date dataFinal=null;
        int quantidadeAProduzir;
        long tempoBrutoExecucao;
        long tempoEfetivoExecucao;
        long tempoADescontar=0;
        List<SuspensaoDeExecucao> suspensaoDeExecucaoList=new ArrayList<>();
        List<MovimentoStock> movimentoStockList=new ArrayList<>();
        List<ProdutoProduzido> produtoProduzidos=new ArrayList<>();
        CodigoInternoMaquina codigoInternoMaquina=null;
        for (Mensagem mensagem:listaDeMensagemAProcessar){
            CriacaoNotificacaoStrategy strategy=factory.getNotificacaoDeErro(mensagem);
            NotificacaoErro notificacaoErro=strategy.validarMensagem(linhaProducao,linhaProducaoRepository,mensagemRepository,mensagem,validacaoParametrosMensagensServico);

            switch (mensagem.mensagemID.tipoDeMensagem) {
                case INICIO_DE_ATIVIDADE:
                    MensagemInicioDeAtividade mensagemInicioDeAtividade=(MensagemInicioDeAtividade)mensagem;
                    codigoInternoMaquina=mensagemInicioDeAtividade.mensagemID.codigoInternoMaquina;
                    ordemProducao=getOrdemProducao(mensagemInicioDeAtividade.getIdentificadorOrdemDeProducao());
                    dataInicio=mensagem.mensagemID.tempoEmissao.timestamp;
                    break;
                case FIM_DE_ATIVIDADE:
                    MensagemFimDeAtividade mensagemFimDeAtividade=(MensagemFimDeAtividade) mensagem;
                    ordemProducao=getOrdemProducao(mensagemFimDeAtividade.getIdentificadorOrdemDeProducao());
                    dataFinal=mensagem.mensagemID.tempoEmissao.timestamp;
                    break;
                case RETOMA_ATIVIDADE:
                    dataRetoma= mensagem.mensagemID.tempoEmissao.timestamp;
                    break;
                case PARAGEM_FORCADA:
                    dataParagem= mensagem.mensagemID.tempoEmissao.timestamp;
                    break;
                case PRODUCAO:
                    MensagemProducao mensagemProducao=(MensagemProducao) mensagem;
                    guardarProdutosProduzidos(mensagemProducao.identificadorDeLote,mensagemProducao.codigoUnico,mensagemProducao.getQuantidade(),produtoProduzidos);
                    break;
               /* case CONSUMO:
                    MensagemConsumo mensagemConsumo=(MensagemConsumo)mensagem;
                    codigoDeposito=mensagemConsumo.codigo;
                    if (codigoDeposito!=null)
                        adicionarMovimentosDeStock(mensagem,movimentoStockList,codigoDeposito);
                    break;
                case ESTORNO:
                    MensagemEstorno mensagemEstorno=(MensagemEstorno) mensagem;
                    codigoDeposito=mensagemEstorno.codigo;
                    adicionarMovimentosDeStock(mensagem,movimentoStockList,codigoDeposito);
                    break;
                case ENTREGA_DE_PRODUCAO:
                    MensagemEntregaDeProducao mensagemEntregaDeProducao=(MensagemEntregaDeProducao) mensagem;
                    codigoDeposito=mensagemEntregaDeProducao.codigo;
                    adicionarMovimentosDeStock(mensagem,movimentoStockList,codigoDeposito);
                    break;
                */
            }

            if (dataRetoma!=null && dataParagem!=null){
                tempoADescontar=tempoADescontar+calcularTempoADescontar(dataParagem,dataRetoma);
                suspensaoDeExecucaoList.add(new SuspensaoDeExecucao(dataRetoma,dataParagem));
                dataRetoma=null;
                dataParagem=null;
            }

            if (notificacaoErro!=null) {
                listaNotificoesDeErros.add(notificacaoErro);
                break;
            }else {
                listaMensagensSemErros.add(mensagem);
            }
        }
        if (!listaMensagensSemErros.isEmpty() && listaNotificoesDeErros.isEmpty() && ordemProducao!=null) {
           // registarActividadeDeMaquina(dataInicio,dataFinal,suspensaoDeExecucaoList,ordemProducao,codigoInternoMaquina,movimentoStockList);
            registarProdutosProduzidos(ordemProducao,produtoProduzidos);
            enriquecerMensagens(idOrdemProducao, listaMensagensSemErros, linhaProducao.identifier);
        }
        if(!listaNotificoesDeErros.isEmpty())
            notificacoesDeErro(listaNotificoesDeErros);
    }

    private Long calcularTempoADescontar(Date dataParagem,Date dataRetoma){
        return dataRetoma.getTime()-dataParagem.getTime();
    }


    private void adicionarMovimentosDeStock(Mensagem mensagem,List<MovimentoStock> list,CodigoDeposito codigoDeposito){
        QuantidadePositiva quantidade=null;
        QuantidadeDeMateriaPrima quantidadeDeMateriaPrima;

        try {
            if (mensagem.mensagemID.tipoDeMensagem.equals(TipoDeMensagem.CONSUMO)) {
                MensagemConsumo mensagemConsumo=(MensagemConsumo) mensagem;
                quantidade = QuantidadePositiva.valueOf(mensagemConsumo.getQuantidadeProduzir());
            }
            if (mensagem.mensagemID.tipoDeMensagem.equals(TipoDeMensagem.ESTORNO)) {
                MensagemEstorno mensagemEstorno=(MensagemEstorno) mensagem;
                quantidade = QuantidadePositiva.valueOf(((MensagemEstorno) mensagem).getQuantidadeProduzir());
            }
            if (mensagem.mensagemID.tipoDeMensagem.equals(TipoDeMensagem.ENTREGA_DE_PRODUCAO)) {
                MensagemEntregaDeProducao mensagemEntregaDeProducao=(MensagemEntregaDeProducao) mensagem;
                quantidade = QuantidadePositiva.valueOf(((MensagemEntregaDeProducao) mensagem).getQuantidadeATransferir());
            }
            //DUVIDA
            MateriaPrima materiaPrima=MateriaPrima.valueOf(TipoDeMateriaPrima.PRODUTO,"");
            quantidadeDeMateriaPrima=QuantidadeDeMateriaPrima.valueOf(quantidade, materiaPrima);
            list.add(new MovimentoStock(codigoDeposito,quantidadeDeMateriaPrima));

        } catch (IllegalDomainValueException e) {
        }

    }


    private void enriquecerMensagens(IdentificadorOrdemProducao identificadorOrdemProducao, List<Mensagem> listaDeMensagensSemErros, IdentificadorLinhaProducao identificadorLinhaProducao) {
        transactionalContext.beginTransaction();
        for (Mensagem mensagem : listaDeMensagensSemErros){
            if (mensagem.enriquecerMensagem(identificadorOrdemProducao, identificadorLinhaProducao))
                mensagemRepository.save(mensagem);
        }
        transactionalContext.commit();
    }

    private void registarActividadeDeMaquina(Date dataInicio, Date dataFinal,List<SuspensaoDeExecucao> suspensaoDeExecucao, OrdemProducao ordemProducao, CodigoInternoMaquina codigoInternoMaquina, List<MovimentoStock> listaDeMovimentos){
        transactionalContext.beginTransaction();
        InicioDeExecucao inicioDeExecucao=new InicioDeExecucao(dataInicio);
        FimDeExecucao fimDeExecucao=new FimDeExecucao(dataFinal);
        UsoDeMaquina usoDeMaquina=new UsoDeMaquina(inicioDeExecucao,fimDeExecucao,codigoInternoMaquina);
        for (MovimentoStock movimentoStock:listaDeMovimentos){
            usoDeMaquina.movimentoStockList.add(movimentoStock);
            movimentoStockRepository.save(movimentoStock);
        }
        usoDeMaquina.suspensaoDeExecucaoList.addAll(suspensaoDeExecucao);
        ordemProducao.usoDeMaquinaList.add(usoDeMaquina);
        usoDeMaquinaRepository.save(usoDeMaquina);
        transactionalContext.commit();
    }

    /**
     * Guarda apenas numa lista os produtos produzidos
     * @param identificadorDeLote Identificador de lote
     * @param codigoUnico   Codigo unico
     * @param quantidade Quantidade produzida
     * @param produtoProduzidoList Lista de produtos produzidos
     */
    private void guardarProdutosProduzidos(IdentificadorDeLote identificadorDeLote, CodigoUnico codigoUnico,int quantidade,List<ProdutoProduzido> produtoProduzidoList){
        ProdutoProduzido produtoProduzido;
        Produto produto;
        Optional<Produto> prdt= produtoRepository.produtoDeCodigoUnico(codigoUnico.codigoUnicoValor);
        if (prdt.isPresent()) {
            produto = prdt.get();
            if (identificadorDeLote==null)
                 produtoProduzido=new ProdutoProduzido(produto,new QuantidadeDeProduto(quantidade,produto.unidadeDeMedida));
            else
                 produtoProduzido=new ProdutoProduzido(produto,identificadorDeLote,new QuantidadeDeProduto(quantidade,produto.unidadeDeMedida));
            produtoProduzidoList.add(produtoProduzido);
        }
    }

    /**
     * Registar produtos produzidos
     * @param ordemProducao Ordem de producao
     * @param produtoProduzidoList Lista de produtos produzidos
     */
    private void registarProdutosProduzidos(OrdemProducao ordemProducao,List<ProdutoProduzido> produtoProduzidoList){
        transactionalContext.beginTransaction();
        ordemProducao.produtosProduzidosList.addAll(produtoProduzidoList);
        for (ProdutoProduzido produtoProduzido: produtoProduzidoList)
            produtoProduzidoRepository.save(produtoProduzido);
        transactionalContext.commit();
        ordemProducaoRepository.save(ordemProducao);
    }

    private OrdemProducao getOrdemProducao(IdentificadorOrdemProducao idOrdemProducao){
        Optional<OrdemProducao> ordemP=ordemProducaoRepository.findByIdentifier(idOrdemProducao);
        if (ordemP.isPresent()){return ordemP.get();}
        return null;
    }

    private void notificacoesDeErro(List<NotificacaoErro> listaNotificacoes){
        for (NotificacaoErro notificacaoErro:listaNotificacoes)
            notificacaoErroRepository.save(notificacaoErro);
    }





}
