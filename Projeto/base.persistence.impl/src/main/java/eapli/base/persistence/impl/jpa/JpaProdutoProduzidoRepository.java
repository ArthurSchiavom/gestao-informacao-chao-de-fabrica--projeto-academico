package eapli.base.persistence.impl.jpa;

import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.Repository.ProdutoProduzidoRepository;
import eapli.base.gestaoproducao.gestaoProdutoProduzido.domain.ProdutoProduzido;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.List;

public class JpaProdutoProduzidoRepository extends JpaAutoTxRepository<ProdutoProduzido, Long, Long> implements ProdutoProduzidoRepository {

    public JpaProdutoProduzidoRepository(TransactionalContext autoTx) {
        super(autoTx, ProdutoProduzido.identityAttributeName());
    }

    public JpaProdutoProduzidoRepository(String persistenceUnitName) {
        super(persistenceUnitName, Application.settings().getExtendedPersistenceProperties(),
                ProdutoProduzido.identityAttributeName());
    }


    @Override
    public List<ProdutoProduzido> findAllList() {
        return Lists.newArrayList(this.findAll());
    }
}
