package eapli.base.persistence.impl.inmemory;

import eapli.base.produto.domain.CodigoUnico;
import eapli.base.produto.domain.Produto;
import eapli.base.produto.persistence.ProdutoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;
import java.util.Optional;

public class InMemoryProdutoRepository extends InMemoryDomainRepository<CodigoUnico, Produto> implements ProdutoRepository {

    @Override
    public Optional<Produto> produtoDeCodigoUnico(String codigoUnico) {
        return this.matchOne(e -> e.codigoUnico.codigoUnicoValor.equals(codigoUnico));
    }

    @Override
    public Optional<Produto> produtoDeCodigoComercial(String codigoComercial) {
        return this.matchOne(e -> e.codigoComercial.codigoComercialValor.equals(codigoComercial));
    }

    @Override
    public List<Produto> produtosSemFichaDeProducao() {
        // TODO
        throw new UnsupportedOperationException();
    }
}
