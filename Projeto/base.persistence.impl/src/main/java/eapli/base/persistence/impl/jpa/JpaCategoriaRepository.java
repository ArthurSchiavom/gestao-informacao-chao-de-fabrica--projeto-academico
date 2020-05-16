package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.gestaoproducao.gestaomaterial.domain.Categoria;
import eapli.base.gestaoproducao.gestaomaterial.domain.CodigoAlfanumerico;
import eapli.base.gestaoproducao.gestaomaterial.repository.CategoriaRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JpaCategoriaRepository extends JpaAutoTxRepository<Categoria, CodigoAlfanumerico, CodigoAlfanumerico>
        implements CategoriaRepository {

    public JpaCategoriaRepository(TransactionalContext autoTx) {
        super(autoTx, Categoria.identityAttributeName());
    }

    public JpaCategoriaRepository(String puname) {
        super(puname, Application.settings().getExtendedPersistenceProperties(),
                Categoria.identityAttributeName());
    }

    @Override
    public Optional<Categoria> findByIdentifier(CodigoAlfanumerico identifier) {
        final Map<String, Object> params = new HashMap<>();
        params.put(Categoria.identityAttributeName(), identifier);
        return matchOne("e."+ Categoria.identityAttributeName()+"=:identifier", params);
        //TODO verificar se isto funciona, é capaz de nao funcionar se o match depender do toString
    }
}
