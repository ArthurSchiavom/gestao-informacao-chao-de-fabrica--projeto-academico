package eapli.base.gestaoproducao.gestaoproduto.application.especificacao;

import eapli.base.gestaoproducao.gestaomaterial.application.dto.MaterialDTO;
import eapli.base.gestaoproducao.gestaomaterial.domain.Material;
import eapli.base.gestaoproducao.gestaomaterial.repository.MaterialRepository;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.MateriaPrima;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.QuantidadeDeMateriaPrima;
import eapli.base.gestaoproducao.gestaomateriaprima.domain.TipoDeMateriaPrima;
import eapli.base.gestaoproducao.gestaoproduto.application.dto.ProdutoDTO;
import eapli.base.gestaoproducao.gestaoproduto.domain.FichaDeProducao;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.medicao.QuantidadePositiva;
import eapli.base.gestaoproducao.gestaoproduto.persistence.FichaDeProducaoRepository;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
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
    private final List<ProdutoDTO> produtosSemFichaDeProducaoDTO;
    private List<QuantidadeDeMateriaPrima> quantidadesDeMateriaPrimaAAdicionar;
    private Produto produtoAlvo;

    public EspecificarFichaDeProducaoController() {
        transactionalContext = PersistenceContext.repositories().newTransactionalContext();
        fichaDeProducaoRepository = PersistenceContext.repositories().fichaDeProducao(transactionalContext);
        produtoRepository = PersistenceContext.repositories().produto(transactionalContext);
        MaterialRepository materialRepository = PersistenceContext.repositories().material();

        List<Material> materiais = materialRepository.findAllList();
        List<Produto> produtos = produtoRepository.findAllList();
        List<Produto> produtosSemFichaDeProducao = produtoRepository.produtosSemFichaDeProducao();

        codigoUnicoToProduto = Collections.unmodifiableMap(EntityUtils.mapIdStringToEntity(produtos));
        codigoInternoToMaterial = Collections.unmodifiableMap(EntityUtils.mapIdStringToEntity(materiais));

        materiaisDTO = Collections.unmodifiableList(DTOUtils.toDTOList(materiais));
        produtosDTO = Collections.unmodifiableList(DTOUtils.toDTOList(produtos));
        produtosSemFichaDeProducaoDTO = Collections.unmodifiableList(DTOUtils.toDTOList(produtosSemFichaDeProducao));

        quantidadesDeMateriaPrimaAAdicionar = new ArrayList<>();
        produtoAlvo = null;
    }

    public List<ProdutoDTO> produtos() {
        return produtosDTO;
    }

    public List<MaterialDTO> materiais() {
        return materiaisDTO;
    }

    public List<ProdutoDTO> produtosSemFichaDeProducao() {
        return produtosSemFichaDeProducaoDTO;
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
        QuantidadeDeMateriaPrima quantidadeDeMateriaPrima = QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(quantidade), materiaPrima);
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
