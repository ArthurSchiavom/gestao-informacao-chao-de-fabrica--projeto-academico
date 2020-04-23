package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaTransactionalContext;

/**
 * a base class for all reporting repositories to use the same persistence unit
 *
 * @param <T>
 * @param <K>
 *
 * @author Paulo Gandra de Sousa
 */
/* package */ class BaseJpaReportingRepositoryBase extends JpaTransactionalContext {

    BaseJpaReportingRepositoryBase() {
        super(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

    BaseJpaReportingRepositoryBase(String persistenceUnitName) {
        super(persistenceUnitName, Application.settings().getExtendedPersistenceProperties());
    }
}
