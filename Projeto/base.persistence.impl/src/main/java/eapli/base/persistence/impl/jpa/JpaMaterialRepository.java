package eapli.base.persistence.impl.jpa;

import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoInternoMaterial;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.gestaoproducao.gestaomaterial.repository.MaterialRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JpaMaterialRepository extends JpaAutoTxRepository<Material, CodigoInternoMaterial, CodigoInternoMaterial> implements MaterialRepository {

    public JpaMaterialRepository(TransactionalContext autoTx) {
        super(autoTx, Material.identityAttributeName());
    }

    public JpaMaterialRepository(String persistenceUnitName) {
        super(persistenceUnitName, Application.settings().getExtendedPersistenceProperties(),
                Material.identityAttributeName());
    }

    @Override
    public List<Material> findAllList() {
        return Lists.newArrayList(this.findAll());
    }

    @Override
    public Optional<Material> obterMaterialPorCodigoInterno(String codigoInterno) {
        final Map<String, Object> params = new HashMap<>();
        params.put("codigo", codigoInterno);
        return matchOne("e.codigoInterno.codigoInternoValor=:codigo", params);
    }
}