package eapli.base.gestaolinhasproducao.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaolinhasproducao.repository.LinhaProducaoRepository;

public class AddLinhaProducaoController {
	//TODO add authz maybe
	private final LinhaProducaoRepository repository = PersistenceContext.repositories().productionLines();

	public LinhaProducao registarLinhaProducao(final String identifier) {
		final LinhaProducao prodLine = new LinhaProducao(identifier);
		return this.repository.save(prodLine);
	}
}
