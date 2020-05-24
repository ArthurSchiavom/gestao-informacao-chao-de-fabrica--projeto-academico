package eapli.base.persistence.impl.inmemory;

import com.google.common.collect.Lists;
import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoproduto.persistence.FichaDeProducaoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;

public class InMemoryFichaDeProducaoRepository  extends InMemoryDomainRepository<Integer, FichaDeProducao> implements FichaDeProducaoRepository  {

	@Override
	public List<FichaDeProducao> findAllList() {
		return Lists.newArrayList(this.findAll());
	}
}
