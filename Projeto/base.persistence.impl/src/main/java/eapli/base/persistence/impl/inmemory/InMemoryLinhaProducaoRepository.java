package eapli.base.persistence.impl.inmemory;

import com.google.common.collect.Lists;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;
import java.util.Optional;

public class InMemoryLinhaProducaoRepository
		extends InMemoryDomainRepository<IdentificadorLinhaProducao, LinhaProducao>
		implements LinhaProducaoRepository {

	@Override
	public Optional<LinhaProducao> findByIdentifier(IdentificadorLinhaProducao identifier) {
		return Optional.of(data().get(identifier));
		//TODO verificar que isto funciona
	}

	@Override
	public List<LinhaProducao> findAllList() {
		return Lists.newArrayList(this.findAll());
	}
}
