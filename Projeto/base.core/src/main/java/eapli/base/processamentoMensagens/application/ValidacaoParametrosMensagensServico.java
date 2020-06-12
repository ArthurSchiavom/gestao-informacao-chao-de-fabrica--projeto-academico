package eapli.base.processamentoMensagens.application;

import eapli.base.gestaoproducao.gestaomensagens.repository.MensagemRepository;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.IdentificadorOrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;

import java.util.Date;
import java.util.Optional;

public class ValidacaoParametrosMensagensServico {
    private OrdemProducaoRepository ordemProducaoRepository;
    private ProdutoRepository produtoRepository;

    public ValidacaoParametrosMensagensServico(ProdutoRepository produtoRepository, OrdemProducaoRepository ordemProducaoRepository) {
        this.produtoRepository=produtoRepository ;
        this.ordemProducaoRepository= ordemProducaoRepository;
    }

    public boolean validarData(Date date){
        if (date.compareTo(new Date())>0)
            return false;
        return true;
    }

    public boolean validarQuantidade(double quantidade){
        return quantidade>0;
    }

    public Produto getProdutoPorCodigoUnico(CodigoUnico codigoUnico){
        Optional<Produto> produto;
        produto=produtoRepository.produtoDeCodigoUnico(codigoUnico.codigoUnicoValor);
        if (!produto.isPresent())
            return null;
        return produto.get();
    }

    public OrdemProducao getOrdemDeProducaoPorIdentificador(IdentificadorOrdemProducao identificador){
        Optional<OrdemProducao> ordemProducao;
        ordemProducao=ordemProducaoRepository.findByIdentifier(identificador);
        if (!ordemProducao.isPresent())
            return null;
        return ordemProducao.get();
    }



}