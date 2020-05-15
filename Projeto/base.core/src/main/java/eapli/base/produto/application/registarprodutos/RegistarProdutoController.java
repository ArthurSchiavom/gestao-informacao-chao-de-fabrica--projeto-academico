package eapli.base.produto.application.registarprodutos;

import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.produto.application.ProdutoBuilder;
import eapli.base.produto.domain.Produto;
import eapli.base.produto.persistence.ProdutoRepository;
import eapli.framework.domain.repositories.TransactionalContext;

import java.util.Optional;

public class RegistarProdutoController {
    private final ProdutoRepository repo;
    private final ProdutoBuilder produtoBuilder;
    private final boolean substituir;
    private String codigoUnicoComDelayRegisto;
    private String codigoComercialComDelayRegisto;

    public RegistarProdutoController(boolean substituir) {
        repo = PersistenceContext.repositories().produto();
        produtoBuilder = new ProdutoBuilder();
        this.substituir = substituir;
        codigoUnicoComDelayRegisto = null;
    }

    /**
     * Para que o sistema de substituição funcione corretamente, este deverá ser o primeiro método a ser invocado
     *
     * @param codigoUnico
     * @throws IllegalDomainValueException
     */
    public void setCodigoUnico(String codigoUnico) {
        codigoUnicoComDelayRegisto = codigoUnico;
    }

    public void setCategoriaDeProduto(String categoriaDeProduto) {
        produtoBuilder.setCategoriaDeProduto(categoriaDeProduto);
    }

    public void setCodigoComercial(String codigoComercial) {
        codigoComercialComDelayRegisto = codigoComercial;
    }

    public void setDescricaoBreve(String descricaoBreve) throws IllegalDomainValueException {
        produtoBuilder.setDescricaoBreve(descricaoBreve);
    }

    public void setDescricaoCompleta(String descricaoCompleta) {
        produtoBuilder.setDescricaoCompleta(descricaoCompleta);
    }

    public void setUnidadeDeMedida(String unidadeDeMedida) {
        produtoBuilder.setUnidadeDeMedida(unidadeDeMedida);
    }

    /**
     * @return (1) true if the operation was successful or (2) false if the builder still doesn't have all obligatory information. (system error)
     */
    public boolean register() throws IllegalDomainValueException {
        if (substituir) {
            Optional<Produto> antigo = repo.produtoDeCodigoUnico(codigoUnicoComDelayRegisto);
            if (antigo.isPresent()) {
                repo.remove(antigo.get());
            }
        }

        produtoBuilder.setCodigoUnico(codigoUnicoComDelayRegisto);
        produtoBuilder.setCodigoComercial(codigoComercialComDelayRegisto);

        if (!produtoBuilder.isReady()) {
            // Should never happen
            return false;
        }

        Produto produto = produtoBuilder.build();
        repo.save(produto);
        return true;
    }
}
