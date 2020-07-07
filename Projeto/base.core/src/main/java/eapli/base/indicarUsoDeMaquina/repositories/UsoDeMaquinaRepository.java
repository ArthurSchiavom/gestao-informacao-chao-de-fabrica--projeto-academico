package eapli.base.indicarUsoDeMaquina.repositories;

import eapli.base.indicarUsoDeMaquina.domain.UsoDeMaquina;
import eapli.base.indicarUsoDeMaquina.domain.UsoDeMaquinaID;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;

public interface UsoDeMaquinaRepository extends DomainRepository<UsoDeMaquinaID, UsoDeMaquina> {
    List<UsoDeMaquina> findAllList();
}
