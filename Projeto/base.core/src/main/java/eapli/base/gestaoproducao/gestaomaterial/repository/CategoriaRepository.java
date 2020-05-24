package eapli.base.gestaoproducao.gestaomaterial.repository;

import eapli.base.gestaoproducao.gestaomaterial.domain.Categoria;
import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoAlfanumericoCategoria;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends DomainRepository<CodigoAlfanumericoCategoria, Categoria> {

    /**
     * Finds a Categoria by it's identifier
     * @param identifier the identifier of the Categoria
     * @return a Categoria or null
     */
    Optional<Categoria> findByIdentifier(CodigoAlfanumericoCategoria identifier);

	/**
	 * @return a list with all the categorias
	 */
	List<Categoria> findAllList();
}
