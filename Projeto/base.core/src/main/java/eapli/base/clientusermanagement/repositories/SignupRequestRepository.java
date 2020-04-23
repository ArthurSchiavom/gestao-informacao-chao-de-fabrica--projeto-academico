package eapli.base.clientusermanagement.repositories;

import eapli.base.clientusermanagement.domain.SignupRequest;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.infrastructure.authz.domain.model.Username;

/**
 *
 * @author Jorge Santos ajs@isep.ipp.pt 02/04/2016
 */
public interface SignupRequestRepository extends DomainRepository<Username, SignupRequest> {

    Iterable<SignupRequest> pendingSignupRequests();
}
