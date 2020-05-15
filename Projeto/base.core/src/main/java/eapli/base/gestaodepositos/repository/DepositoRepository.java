package eapli.base.gestaodepositos.repository;

import eapli.base.gestaodepositos.domain.CodigoDeposito;
import eapli.base.gestaodepositos.domain.Deposito;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

public interface DepositoRepository extends DomainRepository<CodigoDeposito, Deposito> {
	/**
	 * Encontra um deposito pelo seu codigo alfanumerico
	 * @param codigo o codigo a ser procurado
	 * @return um deposito ou null
	 */
	Optional<Deposito> findByCodigo(CodigoDeposito codigo);
}
