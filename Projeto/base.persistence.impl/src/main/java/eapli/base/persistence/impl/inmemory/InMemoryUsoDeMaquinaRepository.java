package eapli.base.persistence.impl.inmemory;

import eapli.base.indicarUsoDeMaquina.domain.UsoDeMaquinaID;
import eapli.base.indicarUsoDeMaquina.domain.UsoDeMaquina;
import eapli.base.indicarUsoDeMaquina.repositories.UsoDeMaquinaRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;

public class InMemoryUsoDeMaquinaRepository extends InMemoryDomainRepository<UsoDeMaquinaID, UsoDeMaquina> implements UsoDeMaquinaRepository {
    @Override
    public List<UsoDeMaquina> findAllList() {
        return null;
    }
}
