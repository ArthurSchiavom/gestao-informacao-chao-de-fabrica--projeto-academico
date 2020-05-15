package eapli.base.gestaoproducao.gestaoprodutos.application;

import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.gestaoproducao.gestaomaterial.domain.MaterialDTO;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.MateriaPrima;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.QuantidadeDeMateriaPrima;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.TipoDeMateriaPrima;
import eapli.base.gestaoproducao.gestaomateriasprimas.repository.MaterialRepository;
import eapli.base.gestaoproducao.gestaoprodutos.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoprodutos.domain.Produto;
import eapli.base.gestaoproducao.gestaoprodutos.domain.QuantidadeZeroMais;
import eapli.base.gestaoproducao.gestaoprodutos.persistence.FichaDeProducaoRepository;
import eapli.base.gestaoproducao.gestaoprodutos.persistence.ProdutoRepository;
import eapli.base.infrastructure.application.DTOUtils;
import eapli.base.infrastructure.application.EntityUtils;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.framework.domain.repositories.TransactionalContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
