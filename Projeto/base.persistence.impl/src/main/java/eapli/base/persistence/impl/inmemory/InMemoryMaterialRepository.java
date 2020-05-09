package eapli.base.persistence.impl.inmemory;

import eapli.base.definircategoriamaterial.domain.CodigoInterno;
import eapli.base.definircategoriamaterial.domain.Material;
import eapli.base.gestaomateriasprimas.repository.MaterialRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;


import java.util.Optional;

public class InMemoryMaterialRepository extends InMemoryDomainRepository<CodigoInterno, Material>
        implements MaterialRepository  {

    @Override
    public Optional<Material> findByCodigoInterno(CodigoInterno codigoInterno) {
        return Optional.of(data().get(codigoInterno));
    }
}
