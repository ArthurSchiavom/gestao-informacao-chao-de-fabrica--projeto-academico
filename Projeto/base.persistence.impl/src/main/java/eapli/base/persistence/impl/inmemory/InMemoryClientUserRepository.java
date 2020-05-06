package eapli.base.persistence.impl.inmemory;

import eapli.base.clientusermanagement.domain.ClientUser;
import eapli.base.clientusermanagement.domain.MecanographicNumber;
import eapli.base.clientusermanagement.repositories.ClientUserRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
public class InMemoryClientUserRepository
        extends InMemoryDomainRepository<MecanographicNumber, ClientUser>
        implements ClientUserRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<ClientUser> findByUsername(Username name) {
        return matchOne(e -> e.user().username().equals(name));
    }

    @Override
    public Optional<ClientUser> findByMecanographicNumber(MecanographicNumber number) {
        return Optional.of(data().get(number));
    }

    @Override
    public Iterable<ClientUser> findAllActive() {
        return match(e -> e.user().isActive());
    }
}
