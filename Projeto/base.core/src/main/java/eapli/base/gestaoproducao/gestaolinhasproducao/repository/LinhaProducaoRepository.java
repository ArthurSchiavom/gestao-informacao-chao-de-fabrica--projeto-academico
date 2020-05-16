package eapli.base.gestaoproducao.gestaolinhasproducao.repository;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

public interface LinhaProducaoRepository extends DomainRepository<IdentificadorLinhaProducao, LinhaProducao> {
	/**
	 * Finds a production line by it's identifier
	 * @param identifier the identifier of the production line we are trying to find
	 * @return a production line or null
	 */
	Optional<LinhaProducao> findByIdentifier(IdentificadorLinhaProducao identifier);
}
