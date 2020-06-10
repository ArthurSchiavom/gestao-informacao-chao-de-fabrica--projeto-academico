package eapli.base.gestaoproducao.movimentos.repositoy;

import eapli.base.gestaoproducao.movimentos.domain.MovimentoStock;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;

public interface MovimentoStockRepository extends DomainRepository<Long, MovimentoStock> {
    List<MovimentoStock> findAllList();
}
