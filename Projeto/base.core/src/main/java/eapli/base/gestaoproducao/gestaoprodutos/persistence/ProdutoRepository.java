package eapli.base.gestaoproducao.gestaoprodutos.persistence;

import eapli.base.gestaoproducao.gestaoprodutos.domain.CodigoUnico;
import eapli.base.gestaoproducao.gestaoprodutos.domain.Produto;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends DomainRepository<CodigoUnico, Produto> {
    Optional<Produto> produtoDeCodigoUnico(String codigoUnico);
    Optional<Produto> produtoDeCodigoComercial(String codigoComercial);
    List<Produto> produtosSemFichaDeProducao();
    List<Produto> findAllList();
}
