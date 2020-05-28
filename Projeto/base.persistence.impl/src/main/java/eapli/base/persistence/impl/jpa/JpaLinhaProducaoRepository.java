package eapli.base.persistence.impl.jpa;

import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.EstadoProcessamentoMensagens;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.repository.LinhaProducaoRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JpaLinhaProducaoRepository
		extends JpaAutoTxRepository<LinhaProducao, IdentificadorLinhaProducao, IdentificadorLinhaProducao>
		implements LinhaProducaoRepository {

	public JpaLinhaProducaoRepository(TransactionalContext autoTx) {
		super(autoTx, LinhaProducao.identityAttributeName());
	}

	public JpaLinhaProducaoRepository(String puname) {
		super(puname, Application.settings().getExtendedPersistenceProperties(),
				LinhaProducao.identityAttributeName());
	}

	@Override
	public Optional<LinhaProducao> findByIdentifier(IdentificadorLinhaProducao identifier) {
		final Map<String, Object> params = new HashMap<>();
		params.put(LinhaProducao.identityAttributeName(), identifier);
		return matchOne("e."+ LinhaProducao.identityAttributeName()+"=:id", params);
		//visto pelo dos profs
	}

	@Override
	public List<LinhaProducao> findAllList() {
		return Lists.newArrayList(this.findAll());
	}
}
