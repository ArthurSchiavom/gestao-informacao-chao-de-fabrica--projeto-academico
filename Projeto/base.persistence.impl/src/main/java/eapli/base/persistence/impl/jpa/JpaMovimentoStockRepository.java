package eapli.base.persistence.impl.jpa;

import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.gestaoproducao.movimentos.domain.MovimentoStock;
import eapli.base.gestaoproducao.movimentos.repositoy.MovimentoStockRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.List;

public class JpaMovimentoStockRepository extends JpaAutoTxRepository<MovimentoStock,Long, Long> implements MovimentoStockRepository {
    public JpaMovimentoStockRepository(TransactionalContext autoTx) {
        super(autoTx, MovimentoStock.identityAttributeName());
    }

    public JpaMovimentoStockRepository(String persistenceUnitName) {
        super(persistenceUnitName, Application.settings().getExtendedPersistenceProperties(),
                MovimentoStock.identityAttributeName());
    }

    @Override
    public List<MovimentoStock> findAllList() {
        return Lists.newArrayList(this.findAll());
    }
}
