package eapli.base.materiaprima.produto.persistence;

import eapli.base.materiaprima.produto.domain.CodigoUnico;
import eapli.base.materiaprima.produto.domain.Produto;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends DomainRepository<CodigoUnico, Produto> {
    Optional<Produto> produtoDeCodigoUnico(String codigoUnico);
    Optional<Produto> produtoDeCodigoComercial(String codigoComercial);
    List<Produto> produtosSemFichaDeProducao();
}
