package eapli.base.persistence.impl.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import eapli.base.Application;
import eapli.base.producao.materiaprima.domain.produto.CodigoUnico;
import eapli.base.producao.materiaprima.domain.produto.Produto;
import eapli.base.producao.materiaprima.persistence.produto.ProdutoRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.model.Username;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

class JpaProdutoRepository
        extends JpaAutoTxRepository<Produto, CodigoUnico, CodigoUnico>
        implements ProdutoRepository {

    public JpaProdutoRepository(TransactionalContext autoTx) {
        super(autoTx, Produto.identityAttributeName());
    }

    public JpaProdutoRepository(String puName) {
        super(puName, Application.settings().getExtendedPersistenceProperties(),
                Produto.identityAttributeName());
    }


    @Override
    public void whatever() {

    }
}