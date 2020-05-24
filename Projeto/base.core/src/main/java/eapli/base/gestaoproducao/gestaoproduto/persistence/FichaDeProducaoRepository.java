package eapli.base.gestaoproducao.gestaoproduto.persistence;

import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;

public interface FichaDeProducaoRepository extends DomainRepository<Integer, FichaDeProducao> {
	/**
	 * @return a list with all fichas de produção
	 */
	List<FichaDeProducao> findAllList();
}
