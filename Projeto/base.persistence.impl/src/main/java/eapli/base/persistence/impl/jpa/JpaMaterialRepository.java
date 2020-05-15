package eapli.base.persistence.impl.jpa;

import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoInterno;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.gestaoproducao.gestaomateriasprimas.repository.MaterialRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

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
    public Optional<Material> findByCodigoInterno(CodigoInterno codigoInterno) {
        final Map<String, Object> params = new HashMap<>();
        params.put(Material.identityAttributeName(), codigoInterno);
        return matchOne("e."+ Material.identityAttributeName()+"=:identifier", params);
    }
    
    @Override
    public List<Material> findAllList() {
        return Lists.newArrayList(this.findAll());
    }
}