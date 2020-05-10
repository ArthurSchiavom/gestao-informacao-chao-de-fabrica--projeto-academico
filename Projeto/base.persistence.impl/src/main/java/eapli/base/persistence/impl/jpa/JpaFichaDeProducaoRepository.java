package eapli.base.persistence.impl.jpa;


import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.definircategoriamaterial.domain.Material;
import eapli.base.produto.domain.FichaDeProducao;
import eapli.base.produto.persistence.FichaDeProducaoRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.List;
import java.util.Optional;

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
}