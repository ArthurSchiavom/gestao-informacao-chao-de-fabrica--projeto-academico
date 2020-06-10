package eapli.base.persistence.impl.inmemory;

import eapli.base.gestaoproducao.gestaoProdutoProduzido.Repository.ProdutoProduzidoRepository;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.domain.ProdutoProduzido;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;

public class InMemoryProdutoProduzido extends InMemoryDomainRepository<Long, ProdutoProduzido> implements ProdutoProduzidoRepository {
    @Override
    public List<ProdutoProduzido> findAllList() {
        return null;
    }
}
