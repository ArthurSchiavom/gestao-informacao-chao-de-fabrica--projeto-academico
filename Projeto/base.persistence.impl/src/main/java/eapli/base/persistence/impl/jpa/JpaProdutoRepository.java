package eapli.base.persistence.impl.jpa;

import com.google.common.collect.Lists;
import eapli.base.Application;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Optional<Produto> produtoDeCodigoUnico(String codigoUnico) {
        final Map<String, Object> params = new HashMap<>();
        params.put("codigo", codigoUnico);
        return matchOne("e.codigoUnico.codigoUnicoValor=:codigo", params);
    }

    @Override
    public Optional<Produto> produtoDeCodigoComercial(String codigoComercial) {
        final Map<String, Object> params = new HashMap<>();
        params.put("codigo", codigoComercial);
        return matchOne("e.codigoComercial.codigoComercialValor=:codigo", params);
    }

    @Override
    public List<Produto> produtosSemFichaDeProducao() {
        return this.createQuery("SELECT p FROM Produto p WHERE p.fichaDeProducao is NULL", Produto.class).getResultList();
    }

    @Override
    public List<Produto> findAllList() {
        return Lists.newArrayList(this.findAll());
    }
}