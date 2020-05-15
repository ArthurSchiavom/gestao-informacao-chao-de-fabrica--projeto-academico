package eapli.base.gestaoproducao.gestaomateriais.repository;

import eapli.base.gestaoproducao.gestaomaterial.domain.Categoria;
import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoAlfanumerico;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.Optional;

public interface CategoriaRepository extends DomainRepository<CodigoAlfanumerico, Categoria> {

    /**
     * Finds a Categoria by it's identifier
     * @param identifier the identifier of the Categoria
     * @return a Categoria or null
     */
    Optional<Categoria> findByIdentifier(CodigoAlfanumerico identifier);
}
