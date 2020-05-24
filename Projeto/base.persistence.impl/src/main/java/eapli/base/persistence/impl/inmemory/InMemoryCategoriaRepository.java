package eapli.base.persistence.impl.inmemory;

import com.google.common.collect.Lists;
import eapli.base.gestaoproducao.gestaomaterial.domain.Categoria;
import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoAlfanumericoCategoria;
import eapli.base.gestaoproducao.gestaomaterial.repository.CategoriaRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;
import java.util.Optional;

public class InMemoryCategoriaRepository extends InMemoryDomainRepository<CodigoAlfanumericoCategoria, Categoria>
        implements CategoriaRepository {

    @Override
    public Optional<Categoria> findByIdentifier(CodigoAlfanumericoCategoria identifier) {
        return Optional.of(data().get(identifier));
        //TODO verificar que isto funciona
    }

    @Override
    public List<Categoria> findAllList() {
        return Lists.newArrayList(this.findAll());
    }
}
