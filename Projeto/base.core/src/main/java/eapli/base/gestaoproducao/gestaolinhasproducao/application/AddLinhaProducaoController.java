package eapli.base.gestaoproducao.gestaolinhasproducao.application;

import eapli.base.gestaoproducao.gestaolinhaproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.base.infrastructure.persistence.PersistenceContext;

public class AddLinhaProducaoController {
	//TODO add authz maybe
	private final LinhaProducaoRepository repository = PersistenceContext.repositories().linhasProducao();

	/**
	 * Regista uma nova linha de produção no repositório
	 * @param identifier o identificador com que queremos identificar a linha de produção
	 * @return a linha de produção instanciada e guardada no repositório
	 */
	public LinhaProducao registarLinhaProducao(final String identifier) {
		final LinhaProducao prodLine = new LinhaProducao(identifier);
		return this.repository.save(prodLine);
	}
}
