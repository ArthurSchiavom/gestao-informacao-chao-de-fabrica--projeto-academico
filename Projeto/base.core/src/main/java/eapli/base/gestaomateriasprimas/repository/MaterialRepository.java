package eapli.base.gestaomateriasprimas.repository;

import eapli.base.definircategoriamaterial.domain.CodigoInterno;
import eapli.base.definircategoriamaterial.domain.Material;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends DomainRepository<CodigoInterno, Material> {
    List<Material> findAllList();
    Optional<Material> obterMaterialPorCodigoInterno(String codigoInterno);
}
