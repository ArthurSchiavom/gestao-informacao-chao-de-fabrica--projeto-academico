package eapli.base.persistence.impl.inmemory;

import eapli.base.gestaoproducao.movimentos.domain.MovimentoStock;
import eapli.base.gestaoproducao.movimentos.repositoy.MovimentoStockRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;

public class InMemoryMovimentoStock extends InMemoryDomainRepository<Long, MovimentoStock> implements MovimentoStockRepository {
    @Override
    public List<MovimentoStock> findAllList() {
        return null;
    }
}
