package eapli.base.gestaoproducao.ordemProducao.repository;

import eapli.base.gestaoproducao.ordemProducao.domain.Estado;
import eapli.base.gestaoproducao.ordemProducao.domain.IdentificadorOrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrdemProducaoRepository  extends DomainRepository<IdentificadorOrdemProducao, OrdemProducao> {

    /**
     * Finds a production line by it's id
     * @param identifier the id of the production line we are trying to find
     * @return a production line or null
     */
    Optional<OrdemProducao> findByIdentifier(IdentificadorOrdemProducao identifier);

    public OrdemProducao findOrdemProducaoByEncomenda(String id);

    public Iterable<OrdemProducao> findOrdemProducaoByEstado(Estado estado);

    boolean saveRewrite(OrdemProducao op);

    List<OrdemProducao> findAllWithDateAfter(Date dataFiltrar);
}
