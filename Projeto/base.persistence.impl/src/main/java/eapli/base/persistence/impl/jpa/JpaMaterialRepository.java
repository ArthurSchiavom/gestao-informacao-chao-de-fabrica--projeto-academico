package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.definircategoriamaterial.domain.CodigoInterno;
import eapli.base.definircategoriamaterial.domain.Material;
import eapli.base.gestaomateriasprimas.repository.MaterialRepository;
import eapli.base.produto.domain.Produto;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import com.google.common.collect.Lists;
import java.util.List;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JpaMaterialRepository extends JpaAutoTxRepository<Material, CodigoInterno, CodigoInterno> implements MaterialRepository {

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