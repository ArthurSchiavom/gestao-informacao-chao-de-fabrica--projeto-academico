package eapli.base.gestaoproducao.gestaomaterial.repository;

import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoInternoMaterial;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends DomainRepository<CodigoInternoMaterial, Material> {
    List<Material> findAllList();
    Optional<Material> obterMaterialPorCodigoInterno(String codigoInterno);
}
