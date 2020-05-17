package eapli.base.gestaoproducao.gestaoproduto.application.especificacao;

import eapli.base.gestaoproducao.gestaoproduto.application.ProdutoBuilder;
import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.FichaDeProducaoRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;

import java.util.Optional;

public class EspecificarProdutoController {
    private final ProdutoBuilder produtoBuilder;
    private final boolean substituir;
    private String codigoUnicoComDelayRegisto;
    private String codigoComercialComDelayRegisto;

    public EspecificarProdutoController(boolean substituir) {
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

    public void setCategoriaDeProduto(String categoriaDeProduto) throws IllegalDomainValueException {
        produtoBuilder.setCategoriaDeProduto(categoriaDeProduto);
    }

    public void setCodigoComercial(String codigoComercial) {
        codigoComercialComDelayRegisto = codigoComercial;
    }

    public void setDescricaoBreve(String descricaoBreve) throws IllegalDomainValueException {
        produtoBuilder.setDescricaoBreve(descricaoBreve);
    }

    public void setDescricaoCompleta(String descricaoCompleta) throws IllegalDomainValueException {
        produtoBuilder.setDescricaoCompleta(descricaoCompleta);
    }

    public void setUnidadeDeMedida(String unidadeDeMedida) throws IllegalDomainValueException {
        produtoBuilder.setUnidadeDeMedida(unidadeDeMedida);
    }

    /**
     * @return (1) true if the operation was successful or (2) false if the builder still doesn't have all obligatory information. (system error)
     */
    public boolean register() throws IllegalDomainValueException {
        RepositoryFactory repositoryFactory = PersistenceContext.repositories();
        TransactionalContext transactionalContext = repositoryFactory.newTransactionalContext();
        ProdutoRepository repoProduto = repositoryFactory.produto();
        FichaDeProducaoRepository repoFichaDeProducao = repositoryFactory.fichaDeProducao();

        transactionalContext.beginTransaction();
        if (substituir) {
            Optional<Produto> antigo = repoProduto.produtoDeCodigoUnico(codigoUnicoComDelayRegisto);
            if (antigo.isPresent()) {
                Produto produto = antigo.get();
                FichaDeProducao fichaDeProducao = produto.fichaDeProducao;

                repoProduto.remove(produto);
                if (fichaDeProducao != null) {
                    repoFichaDeProducao.remove(fichaDeProducao);
                }
            }
        }

        try {
            produtoBuilder.setCodigoUnico(codigoUnicoComDelayRegisto, repoProduto);
            produtoBuilder.setCodigoComercial(codigoComercialComDelayRegisto, repoProduto);
        } catch (IllegalDomainValueException e) {
            transactionalContext.rollback();
            transactionalContext.close();
            throw e;
        }

        if (!produtoBuilder.isReady()) {
            // Should never happen
            transactionalContext.rollback();
            transactionalContext.close();
            return false;
        }

        Produto produto = produtoBuilder.build();
        repoProduto.save(produto);

        transactionalContext.commit();
        transactionalContext.close();
        return true;
    }
}
