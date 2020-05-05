package eapli.base.persistence.impl.inmemory;

import eapli.base.gestaodepositos.domain.CodigoDeposito;
import eapli.base.gestaodepositos.domain.Deposito;
import eapli.base.gestaodepositos.repository.DepositoRepository;
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
