package eapli.base.produto.application;

import eapli.base.definircategoriamaterial.domain.Material;
import eapli.base.definircategoriamaterial.domain.MaterialDTO;
import eapli.base.definircategoriamaterial.repository.MaterialRepository;
import eapli.base.infrastructure.application.DTOUtils;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.materiaprima.application.MateriaPrimaDTO;
import eapli.base.produto.domain.FichaDeProducao;
import eapli.base.produto.domain.Produto;
import eapli.base.produto.persistence.FichaDeProducaoRepository;
import eapli.base.produto.persistence.ProdutoRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaTransactionalContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegistarFichaDeProducaoController {
    private final FichaDeProducaoRepository fichaDeProducaoRepository;
    private final ProdutoRepository produtoRepository;
    private final MaterialRepository materialRepository;
    private final List<Material> materiais;
    private final List<Produto> produtos;
    private final List<MaterialDTO> materiaisDTO;
    private final List<ProdutoDTO> produtosDTO;
    private List<Material> materiaisAAdicionar;
    private List<Produto> produtosAAdicionar;

    public RegistarFichaDeProducaoController() {
        fichaDeProducaoRepository = PersistenceContext.repositories().fichaDeProducao();
        produtoRepository = PersistenceContext.repositories().produto();
        materialRepository = PersistenceContext.repositories().material();
        materiais = Collections.unmodifiableList(new ArrayList<>(materialRepository.findAllList())); // ArrayList para garantir uma ordem fixa
        produtos = Collections.unmodifiableList(new ArrayList<>(produtoRepository.findAllList()));
        materiaisDTO = DTOUtils.toDTOList(materiais);
    }





    public List<ProdutoDTO> produtos() {
        return produtos;
    }

    public List<MaterialDTO> materiais() {
        return materiais;
    }

    public FichaDeProducaoDTO selectProdutoAlvo()
}
