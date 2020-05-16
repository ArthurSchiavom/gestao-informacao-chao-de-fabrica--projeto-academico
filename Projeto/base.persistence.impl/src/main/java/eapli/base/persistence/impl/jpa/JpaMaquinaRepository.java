package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.gestaoproducao.gestaomaquina.domain.CodigoInterno;
import eapli.base.gestaoproducao.gestaomaquina.domain.Maquina;
import eapli.base.gestaoproducao.gestaomaquina.repository.MaquinaRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaMaquinaRepository extends JpaAutoTxRepository<Maquina, CodigoInterno, CodigoInterno>
        implements MaquinaRepository {

    public JpaMaquinaRepository(TransactionalContext autoTx) {
        super(autoTx, Maquina.identityAttributeName());
    }

    public JpaMaquinaRepository(String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                Maquina.identityAttributeName());
    }

    @Override
    public Optional<Maquina> findByIdentifier(CodigoInterno identifier) {
        final Map<String, Object> params = new HashMap<>();
        params.put(Maquina.identityAttributeName(), identifier);
        return matchOne("e."+ Maquina.identityAttributeName()+"=:identifier", params);
    }

}
