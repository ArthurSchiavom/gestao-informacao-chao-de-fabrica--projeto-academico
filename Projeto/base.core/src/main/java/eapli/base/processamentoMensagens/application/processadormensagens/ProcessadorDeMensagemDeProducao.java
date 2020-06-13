package eapli.base.processamentoMensagens.application.processadormensagens;

import eapli.base.gestaoproducao.gestaoProdutoProduzido.Repository.ProdutoProduzidoRepository;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.domain.IdentificadorDeLote;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.domain.ProdutoProduzido;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.domain.QuantidadeDeProduto;
import eapli.base.gestaoproducao.gestaoerrosnotificacao.domain.NotificacaoErro;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInternoMaquina;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomensagens.domain.Mensagem;
import eapli.base.gestaoproducao.gestaomensagens.domain.MensagemProducao;
import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.indicarUsoDeMaquina.repositories.UsoDeMaquinaRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.processamentoMensagens.application.ValidacaoParametrosMensagensServico;
import eapli.base.processamentoMensagens.application.tiposMensagensNotificacao.ValidadorMensagem;


public class ProcessadorDeMensagemDeProducao implements ProcessadorMensagem {

    private ValidacaoParametrosMensagensServico validacaoParametrosMensagensServico;
    private LinhaProducaoRepository linhaProducaoRepository;
    private ProdutoRepository produtoRepository;
    private OrdemProducaoRepository ordemProducaoRepository;
    private UsoDeMaquinaRepository usoDeMaquinaRepository;
    private MensagemRepository mensagemRepository;
    private ProdutoProduzidoRepository produtoProduzidoRepository;

    public ProcessadorDeMensagemDeProducao() {
        this.ordemProducaoRepository= PersistenceContext.repositories().ordemProducao();
        this.produtoRepository=PersistenceContext.repositories().produto();
        this.usoDeMaquinaRepository=PersistenceContext.repositories().usoDeMaquina();
        this.linhaProducaoRepository= PersistenceContext.repositories().linhasProducao();
        this.validacaoParametrosMensagensServico=new ValidacaoParametrosMensagensServico();
        this.mensagemRepository=PersistenceContext.repositories().mensagem();
        this.produtoProduzidoRepository=PersistenceContext.repositories().produtoProduzido();
    }

    @Override
    public NotificacaoErro processarMensagem(Mensagem mensagem, OrdemProducao ordemProducao, ValidadorMensagem validadorMensagem) {
        MensagemProducao mensagemProducao=(MensagemProducao)mensagem;
        CodigoInternoMaquina codigoInternoMaquina=mensagemProducao.mensagemID.codigoInternoMaquina;
        Maquina maquina=validacaoParametrosMensagensServico.getMaquinaPorIdentificador(codigoInternoMaquina);
        LinhaProducao linhaProducao=validacaoParametrosMensagensServico.getLinhaDeProducaoPorIdentificador(maquina.getLinhaProducao());
        NotificacaoErro notificacaoErro=validadorMensagem.validarMensagem(mensagemRepository,mensagemProducao,validacaoParametrosMensagensServico);
        if (notificacaoErro!=null){return notificacaoErro;}
        guardarProdutosProduzidos(mensagemProducao.identificadorDeLote,mensagemProducao.codigoUnico,mensagemProducao.getQuantidade(),ordemProducao);
        return null;
    }

    /**
     * Guarda apenas numa lista os produtos produzidos
     * @param identificadorDeLote Identificador de lote
     * @param codigoUnico   Codigo unico
     * @param quantidade Quantidade produzida
     */
    private void guardarProdutosProduzidos(IdentificadorDeLote identificadorDeLote, CodigoUnico codigoUnico, double quantidade,OrdemProducao ordemProducao){
        ProdutoProduzido produtoProduzido;
        Produto produto=validacaoParametrosMensagensServico.getProdutoPorCodigoUnico(codigoUnico);
        if (identificadorDeLote==null)
            produtoProduzido=new ProdutoProduzido(produto,new QuantidadeDeProduto(quantidade,produto.unidadeDeMedida));
        else
            produtoProduzido=new ProdutoProduzido(produto,identificadorDeLote,new QuantidadeDeProduto(quantidade,produto.unidadeDeMedida));
        ordemProducao.produtosProduzidosList.add(produtoProduzido);
        produtoProduzidoRepository.save(produtoProduzido);
        ordemProducaoRepository.save(ordemProducao);
    }

}
