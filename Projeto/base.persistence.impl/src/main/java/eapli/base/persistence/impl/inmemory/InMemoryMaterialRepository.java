
package eapli.base.persistence.impl.inmemory;

import com.google.common.collect.Lists;
import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoInternoMaterial;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.gestaoproducao.gestaomaterial.repository.MaterialRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import java.util.List;
import java.util.Optional;

public class InMemoryMaterialRepository extends InMemoryDomainRepository<CodigoInternoMaterial, Material> implements MaterialRepository {

   
    @Override
    public List<Material> findAllList() {
        return Lists.newArrayList(this.findAll());
    }

    @Override
    public Optional<Material> obterMaterialPorCodigoInterno(String codigoInterno) {
        return Optional.of(data().get(codigoInterno));
    }
}

