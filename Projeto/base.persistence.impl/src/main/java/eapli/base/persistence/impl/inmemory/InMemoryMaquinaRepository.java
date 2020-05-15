package eapli.base.persistence.impl.inmemory;

import eapli.base.registarmaquina.domain.CodigoInterno;
import eapli.base.registarmaquina.domain.Maquina;
import eapli.base.registarmaquina.repository.MaquinaRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

public class InMemoryMaquinaRepository extends InMemoryDomainRepository<CodigoInterno, Maquina>
        implements MaquinaRepository {
    @Override
    public Optional<Maquina> findByIdentifier(CodigoInterno identifier) {
        return Optional.empty();
    }
}
