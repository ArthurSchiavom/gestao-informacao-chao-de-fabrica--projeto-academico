package eapli.base.persistence.impl.jpa;


import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoproduto.persistence.FichaDeProducaoRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.List;

class JpaFichaDeProducaoRepository
        extends JpaAutoTxRepository<FichaDeProducao, Integer, Integer>
        implements FichaDeProducaoRepository {

    public JpaFichaDeProducaoRepository(TransactionalContext autoTx) {
        super(autoTx, FichaDeProducao.identityAttributeName());
    }

    public JpaFichaDeProducaoRepository(String puName) {
        super(puName, Application.settings().getExtendedPersistenceProperties(),
                FichaDeProducao.identityAttributeName());
    }

    @Override
    public List<FichaDeProducao> findAllList() {
        return Lists.newArrayList(this.findAll());
    }
}