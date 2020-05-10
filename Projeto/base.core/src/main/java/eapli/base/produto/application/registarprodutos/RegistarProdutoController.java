package eapli.base.produto.application.registarprodutos;

import eapli.base.infrastructure.domain.IllegalDomainValue;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.produto.application.ProdutoBuilder;
import eapli.base.produto.domain.Produto;
import eapli.base.produto.persistence.ProdutoRepository;
import eapli.framework.domain.repositories.TransactionalContext;

import java.util.Optional;

public class RegistarProdutoController {
    private final TransactionalContext transactionalContext;
    private final ProdutoRepository repo;
    private final ProdutoBuilder produtoBuilder;
    private final boolean subtituir;
    private Produto aRemover;
    private String codigoUnicoComDelayRegisto;

    public RegistarProdutoController(boolean subtituir) {
        transactionalContext = PersistenceContext.repositories().newTransactionalContext();
        repo = PersistenceContext.repositories().produto(transactionalContext);
        produtoBuilder = new ProdutoBuilder();
        this.subtituir = subtituir;
        aRemover = null;
        codigoUnicoComDelayRegisto = null;
    }

    /**
     * Para que o sistema de substituição funcione corretamente, este deverá ser o primeiro método a ser invocado
     *
     * @param codigoUnico
     * @throws IllegalDomainValue
     */
    public void setCodigoUnico(String codigoUnico) throws IllegalDomainValue {
        if (subtituir) {
            Optional<Produto> antigo = repo.produtoDeCodigoUnico(codigoUnico);
            if (antigo.isPresent()) {
                aRemover = antigo.get();
                codigoUnicoComDelayRegisto = codigoUnico;
            }
        }
        produtoBuilder.setCodigoUnico(codigoUnico);
    }

    public void setCategoriaDeProduto(String categoriaDeProduto) {
        produtoBuilder.setCategoriaDeProduto(categoriaDeProduto);
    }

    public void setCodigoComercial(String codigoComercial) throws IllegalDomainValue {
        produtoBuilder.setCodigoComercial(codigoComercial);
    }

    public void setDescricaoBreve(String descricaoBreve) throws IllegalDomainValue {
        produtoBuilder.setDescricaoBreve(descricaoBreve);
    }

    public void setDescricaoCompleta(String descricaoCompleta) {
        produtoBuilder.setDescricaoCompleta(descricaoCompleta);
    }

    public void setUnidadeDeMedida(String unidadeDeMedida) {
        produtoBuilder.setUnidadeDeMedida(unidadeDeMedida);
    }

    /**
     *
     * @return (1) true if the operation was successful or (2) false if the builder still doesn't have all obligatory information. (system error)
     */
    public boolean register() {
        if (!produtoBuilder.isReady()) {
            return false;
        }

        transactionalContext.beginTransaction();
        if (aRemover != null) {
            repo.remove(aRemover);
        }

        Produto produto = produtoBuilder.build();
        repo.save(produto);
        transactionalContext.commit();
        return true;
    }
}
