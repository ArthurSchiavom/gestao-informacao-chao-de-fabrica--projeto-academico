package eapli.base.gestaoproducao.ordemProducao.repository;

import eapli.base.gestaoproducao.ordemProducao.domain.Estado;
import eapli.base.gestaoproducao.ordemProducao.domain.Identificador;
import eapli.base.gestaoproducao.ordemProducao.domain.IdentificadorEncomenda;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

public interface OrdemProducaoRepository  extends DomainRepository<Identificador, OrdemProducao> {

    /**
     * Finds a production line by it's identifier
     * @param identifier the identifier of the production line we are trying to find
     * @return a production line or null
     */
    Optional<OrdemProducao> findByIdentifier(Identificador identifier);

    public OrdemProducao findOrdemProducaoByEncomenda(String id);

    public Iterable<OrdemProducao> findOrdemProducaoByEstado(Estado estado);

    public boolean saveRewrite(OrdemProducao op);

}
