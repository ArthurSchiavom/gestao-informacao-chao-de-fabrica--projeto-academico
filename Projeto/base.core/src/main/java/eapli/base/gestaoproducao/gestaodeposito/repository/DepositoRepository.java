package eapli.base.gestaoproducao.gestaodeposito.repository;

import eapli.base.gestaoproducao.gestaodeposito.domain.CodigoDeposito;
import eapli.base.gestaoproducao.gestaodeposito.domain.Deposito;
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
