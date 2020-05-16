package eapli.base.persistence.impl.inmemory;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaodeposito.domain.Deposito;
import eapli.base.gestaoproducao.gestaodeposito.repository.DepositoRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

public class InMemoryDepositRepository
		extends InMemoryDomainRepository<CodigoDeposito, Deposito>
		implements DepositoRepository {
	@Override
	public Optional<Deposito> findByCodigo(CodigoDeposito codigo) {
		return Optional.of(data().get(codigo));
	}
}
