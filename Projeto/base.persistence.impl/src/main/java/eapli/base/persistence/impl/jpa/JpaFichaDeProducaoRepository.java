package eapli.base.persistence.impl.jpa;


import eapli.base.Application;
import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoproduto.persistence.FichaDeProducaoRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

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