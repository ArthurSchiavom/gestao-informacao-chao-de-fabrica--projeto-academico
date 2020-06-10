package eapli.base.persistence.impl.jpa;

import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.indicarUsoDeMaquina.domain.UsoDeMaquinaID;
import eapli.base.indicarUsoDeMaquina.domain.UsoDeMaquina;
import eapli.base.indicarUsoDeMaquina.repositories.UsoDeMaquinaRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.List;

public class JpaUsoDeMaquinaRepository extends JpaAutoTxRepository<UsoDeMaquina, UsoDeMaquinaID, UsoDeMaquinaID> implements UsoDeMaquinaRepository {

    public JpaUsoDeMaquinaRepository(TransactionalContext autoTx) {
        super(autoTx, UsoDeMaquina.identityAttributeName());
    }

    public JpaUsoDeMaquinaRepository(String persistenceUnitName) {
        super(persistenceUnitName, Application.settings().getExtendedPersistenceProperties(),
                UsoDeMaquina.identityAttributeName());
    }

    @Override
    public List<UsoDeMaquina> findAllList() {
        return Lists.newArrayList(this.findAll());
    }
}
