package eapli.base.producao.materiaprima.produto.persistence;

import eapli.base.producao.materiaprima.produto.domain.CodigoUnico;
import eapli.base.producao.materiaprima.produto.domain.Produto;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

public interface ProdutoRepository extends DomainRepository<CodigoUnico, Produto> {
    Optional<Produto> produtoOfCodigoUnico(String codigoUnico);
    Optional<Produto> produtoOfCodigoComercial(String codigoComercial);
}
