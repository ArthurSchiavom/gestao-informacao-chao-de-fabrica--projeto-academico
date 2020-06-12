package eapli.base.gestaoproducao.gestaolinhasproducao.repository;

import eapli.base.gestaoproducao.gestaolinhasproducao.domain.IdentificadorLinhaProducao;
import eapli.base.gestaoproducao.gestaolinhasproducao.domain.LinhaProducao;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface LinhaProducaoRepository extends DomainRepository<IdentificadorLinhaProducao, LinhaProducao> {
	/**
	 * Finds a production line by it's id
	 *
	 * @param identifier the id of the production line we are trying to find
	 * @return a production line or null
	 */
	Optional<LinhaProducao> findByIdentifier(IdentificadorLinhaProducao identifier);

	/**
	 * @return a list with all fichas de produção
	 */
	List<LinhaProducao> findAllList();

	/**
	 * Vai buscar uma página de resultados
	 *
	 * @param pageSize o tamanho da página (100 vai dar 100 resultados)
	 * @param pageNum  o número da página(0 vai dar a primeira página)
	 * @return uma lista com pageSize resultados
	 */
	List<LinhaProducao> findPage(int pageSize, int pageNum);
}
