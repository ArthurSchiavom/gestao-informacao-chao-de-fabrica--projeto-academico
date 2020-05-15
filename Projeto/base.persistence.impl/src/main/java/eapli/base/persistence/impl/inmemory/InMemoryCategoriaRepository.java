package eapli.base.persistence.impl.inmemory;

import eapli.base.gestaoproducao.gestaomaterial.domain.Categoria;
import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoAlfanumerico;
import eapli.base.gestaoproducao.gestaomateriais.repository.CategoriaRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.Optional;

public class InMemoryCategoriaRepository extends InMemoryDomainRepository<CodigoAlfanumerico, Categoria>
        implements CategoriaRepository {

    @Override
    public Optional<Categoria> findByIdentifier(CodigoAlfanumerico identifier) {
        return Optional.of(data().get(identifier));
        //TODO verificar que isto funciona
    }
}
