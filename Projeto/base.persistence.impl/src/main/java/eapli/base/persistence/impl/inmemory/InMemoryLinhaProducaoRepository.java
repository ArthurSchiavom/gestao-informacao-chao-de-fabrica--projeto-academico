package eapli.base.persistence.impl.inmemory;

import eapli.base.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

public class InMemoryLinhaProducaoRepository
		extends InMemoryDomainRepository<IdentificadorLinhaProducao, LinhaProducao>
		implements LinhaProducaoRepository {

	@Override
	public Optional<LinhaProducao> findByIdentifier(IdentificadorLinhaProducao identifier) {
		return Optional.of(data().get(identifier));
		//TODO verificar que isto funciona
	}
}
