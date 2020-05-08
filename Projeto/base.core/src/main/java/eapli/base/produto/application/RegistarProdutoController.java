package eapli.base.materiaprima.produto.application;

import eapli.base.infrastructure.domain.IllegalDomainValue;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.materiaprima.produto.domain.Produto;
import eapli.base.materiaprima.produto.persistence.ProdutoRepository;

import java.util.Optional;

public class RegistarProdutoController {
    private final ProdutoRepository repo;
    private final ProdutoBuilder produtoBuilder;
    private final boolean subtituir;

    public RegistarProdutoController(boolean subtituir) {
        repo = PersistenceContext.repositories().produto();
        produtoBuilder = new ProdutoBuilder();
        this.subtituir = subtituir;
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
                repo.remove(antigo.get());
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

        Produto produto = produtoBuilder.build();
        repo.save(produto);
        return true;
    }
}
