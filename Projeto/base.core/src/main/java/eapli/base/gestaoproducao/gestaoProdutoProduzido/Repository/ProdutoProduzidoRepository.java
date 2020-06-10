package eapli.base.gestaoproducao.gestaoProdutoProduzido.Repository;

import eapli.base.gestaoproducao.gestaoProdutoProduzido.domain.ProdutoProduzido;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;

public interface ProdutoProduzidoRepository extends DomainRepository<Long, ProdutoProduzido> {
    List<ProdutoProduzido> findAllList();
}
