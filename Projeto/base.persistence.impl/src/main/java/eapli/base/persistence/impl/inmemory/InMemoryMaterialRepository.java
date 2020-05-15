
package eapli.base.persistence.impl.inmemory;

import com.google.common.collect.Lists;
import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoInterno;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.gestaoproducao.gestaomateriasprimas.repository.MaterialRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;
import java.util.Optional;

public class InMemoryMaterialRepository extends InMemoryDomainRepository<CodigoInterno, Material> implements MaterialRepository {

    @Override
    public Optional<Material> findByCodigoInterno(CodigoInterno codigoInterno) {
        return Optional.of(data().get(codigoInterno));
    }
   
    @Override
    public List<Material> findAllList() {
        return Lists.newArrayList(this.findAll());
    }
}

