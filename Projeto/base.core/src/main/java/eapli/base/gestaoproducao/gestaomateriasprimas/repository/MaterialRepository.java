package eapli.base.gestaoproducao.gestaomateriasprimas.repository;

import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoInterno;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends DomainRepository<CodigoInterno, Material> {
    Optional<Material> findByCodigoInterno(CodigoInterno codigoInterno);
    List<Material> findAllList();
}
