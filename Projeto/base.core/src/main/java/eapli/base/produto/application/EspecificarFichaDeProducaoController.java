package eapli.base.produto.application;

import eapli.base.definircategoriamaterial.domain.Material;
import eapli.base.definircategoriamaterial.domain.MaterialDTO;
import eapli.base.gestaomateriasprimas.repository.MaterialRepository;
import eapli.base.infrastructure.application.DTOUtils;
import eapli.base.infrastructure.application.EntityUtils;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.materiaprima.domain.MateriaPrima;
import eapli.base.materiaprima.domain.QuantidadeDeMateriaPrima;
import eapli.base.materiaprima.domain.TipoDeMateriaPrima;
import eapli.base.produto.domain.FichaDeProducao;
import eapli.base.produto.domain.Produto;
import eapli.base.produto.domain.QuantidadeZeroMais;
import eapli.base.produto.persistence.FichaDeProducaoRepository;
import eapli.base.produto.persistence.ProdutoRepository;
import eapli.framework.domain.repositories.TransactionalContext;

import java.util.*;

public class EspecificarFichaDeProducaoController {
    private final TransactionalContext transactionalContext;
    private final FichaDeProducaoRepository fichaDeProducaoRepository;
    private final ProdutoRepository produtoRepository;
    private final Map<String, Produto> codigoUnicoToProduto;
    private final Map<String, Material> codigoInternoToMaterial;
    private final List<MaterialDTO> materiaisDTO;
    private final List<ProdutoDTO> produtosDTO;
    private List<QuantidadeDeMateriaPrima> quantidadesDeMateriaPrimaAAdicionar;
    private Produto produtoAlvo;

    public EspecificarFichaDeProducaoController() {
        transactionalContext = PersistenceContext.repositories().newTransactionalContext();
        fichaDeProducaoRepository = PersistenceContext.repositories().fichaDeProducao(transactionalContext);
        produtoRepository = PersistenceContext.repositories().produto(transactionalContext);
        MaterialRepository materialRepository = PersistenceContext.repositories().material();

        List<Material> materiais = materialRepository.findAllList();
        List<Produto> produtos = produtoRepository.findAllList();

        codigoUnicoToProduto = Collections.unmodifiableMap(EntityUtils.mapIdStringToEntity(produtos));
        codigoInternoToMaterial = Collections.unmodifiableMap(EntityUtils.mapIdStringToEntity(materiais));

        materiaisDTO = Collections.unmodifiableList(DTOUtils.toDTOList(materiais));
        produtosDTO = Collections.unmodifiableList(DTOUtils.toDTOList(produtos));

        quantidadesDeMateriaPrimaAAdicionar = new ArrayList<>();
        produtoAlvo = null;
    }

    public List<ProdutoDTO> produtos() {
        return produtosDTO;
    }

    public List<MaterialDTO> materiais() {
        return materiaisDTO;
    }

    /**
     *
     * @param codigoUnico
     * @return (1) true em caso de sucesso ou (2) caso o ID não esteja registado
     * @throws IllegalDomainValueException
     */
    public boolean selecionarProdutoAlvo(String codigoUnico) {
        produtoAlvo = codigoUnicoToProduto.get(codigoUnico);
        return produtoAlvo != null;
    }

    private void adicionarMateriaPrima(MateriaPrima materiaPrima, double quantidade) throws IllegalDomainValueException {
        QuantidadeDeMateriaPrima quantidadeDeMateriaPrima = QuantidadeDeMateriaPrima.valueOf(QuantidadeZeroMais.valueOf(quantidade), materiaPrima);
        quantidadesDeMateriaPrimaAAdicionar.add(quantidadeDeMateriaPrima);
    }

    public void adicionarProduto(String codigoUnico, double quantidade) throws IllegalDomainValueException {
        Produto produto = codigoUnicoToProduto.get(codigoUnico);
        if (produto == null) {
            throw new IllegalDomainValueException("O código único especificado não está associado a nenhum produto", IllegalDomainValueType.DOESNT_EXIST);
        }
        MateriaPrima materiaPrima = MateriaPrima.valueOf(TipoDeMateriaPrima.PRODUTO, codigoUnico);
        adicionarMateriaPrima(materiaPrima, quantidade);
    }

    public void adicionarMaterial(String codigoInterno, double quantidade) throws IllegalDomainValueException {
        Material material = codigoInternoToMaterial.get(codigoInterno);
        if (material == null) {
            throw new IllegalDomainValueException("O código interno especificado não está associado a nenhum material", IllegalDomainValueType.DOESNT_EXIST);
        }
        MateriaPrima materiaPrima = MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, codigoInterno);
        adicionarMateriaPrima(materiaPrima, quantidade);
    }

    public void registar() throws IllegalDomainValueException {
        FichaDeProducao fichaDeProducaoNova = FichaDeProducao.valueOf(quantidadesDeMateriaPrimaAAdicionar);
        FichaDeProducao fichaDeProducaoAntiga = produtoAlvo.fichaDeProducao;

        transactionalContext.beginTransaction();

        fichaDeProducaoNova = fichaDeProducaoRepository.save(fichaDeProducaoNova);
        produtoAlvo.fichaDeProducao = fichaDeProducaoNova;
        produtoAlvo = produtoRepository.save(produtoAlvo);

        if (fichaDeProducaoAntiga != null) {
            fichaDeProducaoRepository.remove(fichaDeProducaoAntiga);
        }

        transactionalContext.commit();
    }
}
