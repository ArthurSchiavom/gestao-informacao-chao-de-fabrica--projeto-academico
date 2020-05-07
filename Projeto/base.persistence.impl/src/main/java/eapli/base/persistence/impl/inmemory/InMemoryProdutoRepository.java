package eapli.base.persistence.impl.inmemory;

import eapli.base.producao.materiaprima.produto.domain.CodigoUnico;
import eapli.base.producao.materiaprima.produto.domain.Produto;
import eapli.base.producao.materiaprima.produto.persistence.ProdutoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

public class InMemoryProdutoRepository extends InMemoryDomainRepository<CodigoUnico, Produto> implements ProdutoRepository {

    @Override
    public Optional<Produto> produtoOfCodigoUnico(String codigoUnico) {
        return this.matchOne(e -> e.codigoUnico.codigoUnicoValor.equals(codigoUnico));
    }

    @Override
    public Optional<Produto> produtoOfCodigoComercial(String codigoComercial) {
        return this.matchOne(e -> e.codigoComercial.codigoComercialValor.equals(codigoComercial));
    }
}
