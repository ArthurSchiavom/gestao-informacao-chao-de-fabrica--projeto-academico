package eapli.base.persistence.impl.jpa;

<<<<<<< Updated upstream
import eapli.base.Application;
import eapli.base.definircategoriamaterial.domain.CodigoInterno;
import eapli.base.definircategoriamaterial.domain.Material;
import eapli.base.gestaomateriasprimas.repository.MaterialRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import com.google.common.collect.Lists;
import java.util.List;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaMaterialRepository extends JpaAutoTxRepository<Material, CodigoInterno, CodigoInterno> implements MaterialRepository {
=======
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.definircategoriamaterial.domain.CodigoInterno;
import eapli.base.definircategoriamaterial.domain.Material;
import eapli.base.definircategoriamaterial.repository.MaterialRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

class JpaMaterialRepository
        extends JpaAutoTxRepository<Material, CodigoInterno, CodigoInterno>
        implements MaterialRepository {
>>>>>>> Stashed changes

    public JpaMaterialRepository(TransactionalContext autoTx) {
        super(autoTx, Material.identityAttributeName());
    }

<<<<<<< Updated upstream
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
    
=======
    public JpaMaterialRepository(String puName) {
        super(puName, Application.settings().getExtendedPersistenceProperties(),
                Material.identityAttributeName());
    }


>>>>>>> Stashed changes
    @Override
    public List<Material> findAllList() {
        return Lists.newArrayList(this.findAll());
    }
}