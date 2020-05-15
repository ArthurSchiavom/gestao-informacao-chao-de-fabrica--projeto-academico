package eapli.base.registarmaquina.repository;

import eapli.base.registarmaquina.domain.CodigoInterno;
import eapli.base.registarmaquina.domain.Maquina;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

public interface MaquinaRepository extends DomainRepository<CodigoInterno, Maquina> {

    /**
     * procura uma máquina pelo identificdor
     * @param identifier identificador da maquina que está a procura
     * @return máquina ou null
     */
    Optional<Maquina> findByIdentifier(CodigoInterno identifier);
}
