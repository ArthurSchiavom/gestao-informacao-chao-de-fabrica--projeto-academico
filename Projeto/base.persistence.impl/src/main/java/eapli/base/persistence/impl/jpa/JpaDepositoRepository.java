package eapli.base.persistence.impl.jpa;

import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaodeposito.domain.Deposito;
import eapli.base.gestaoproducao.gestaodeposito.repository.DepositoRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JpaDepositoRepository
		extends JpaAutoTxRepository<Deposito, CodigoDeposito, CodigoDeposito>
		implements DepositoRepository {
	public JpaDepositoRepository(TransactionalContext autoTx) {
		super(autoTx, Deposito.identityAttributeName());
	}

	public JpaDepositoRepository(String puname) {
		super(puname, Application.settings().getExtendedPersistenceProperties(),
				Deposito.identityAttributeName());
	}

	@Override
	public Optional<Deposito> findByCodigo(CodigoDeposito codigo) {
		final Map<String, Object> params = new HashMap<>();
		params.put(Deposito.identityAttributeName(), codigo);
		return matchOne("e."+ Deposito.identityAttributeName()+"=:codigo", params);

	}

	@Override
	public List<Deposito> findAllList() {
		return Lists.newArrayList(this.findAll());
	}
}
