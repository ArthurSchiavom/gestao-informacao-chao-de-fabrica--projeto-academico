package eapli.base.gestaoproducao.ordemProducao.application;

import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.*;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.base.infrastructure.persistence.PersistenceContext;

import javax.annotation.Nullable;
import java.util.*;

public class OrdemProducaoBuilder {
    private IdentificadorOrdemProducao identificadorOrdemProducao;
    private Date dataEmissao;
    private Date dataPrevistaExecucao;
    private CodigoUnico codigoUnicoProduto;
    private QuantidadeAProduzir quantidadeAProduzir;
    private Set<IdentificadorEncomenda> identificadoresEncomendas;

    public OrdemProducaoBuilder() {
        identificadoresEncomendas = new HashSet<>();
    }

    private ProdutoRepository buscarRepositorioEfetivoProduto(@Nullable ProdutoRepository produtoRepository) {
        if (produtoRepository != null)
            return produtoRepository;
        else
            return PersistenceContext.repositories().produto();
    }

    private OrdemProducaoRepository buscarRepositorioEfetivoOrdem(@Nullable OrdemProducaoRepository repo) {
        if (repo != null)
            return repo;
        else
            return PersistenceContext.repositories().ordemProducao();
    }

    public void setIdentificadorOrdemProducao(String identificadorOrdemProducao, @Nullable OrdemProducaoRepository ordemProducaoRepository) throws IllegalDomainValueException {
        IdentificadorOrdemProducao id = new IdentificadorOrdemProducao(identificadorOrdemProducao);
        ordemProducaoRepository = buscarRepositorioEfetivoOrdem(ordemProducaoRepository);

        Optional<OrdemProducao> possivelOrdem = ordemProducaoRepository.findByIdentifier(id);
        if (possivelOrdem.isPresent()) {
            throw new IllegalDomainValueException("O identificador de ordem de produção já existe", IllegalDomainValueType.ALREADY_EXISTS);
        }

        this.identificadorOrdemProducao = id;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public void setDataPrevistaExecucao(Date dataPrevistaExecucao) throws IllegalDomainValueException {
        if (dataEmissao != null && dataPrevistaExecucao.compareTo(dataEmissao) < 0) {
            throw new IllegalDomainValueException("A data prevista de execução deve ser posterior à data de emissão", IllegalDomainValueType.ILLEGAL_VALUE);
        }
        this.dataPrevistaExecucao = dataPrevistaExecucao;
    }

    public void setCodigoUnicoProduto(String codigoUnicoProduto, @Nullable ProdutoRepository produtoRepository) throws IllegalDomainValueException {
        Optional<Produto> produto = buscarRepositorioEfetivoProduto(produtoRepository).produtoDeCodigoUnico(codigoUnicoProduto);
        if (produto.isPresent()) {
            this.codigoUnicoProduto = produto.get().codigoUnico;
        }
        else {
            throw new IllegalDomainValueException("O produto especificado não se encontra registado", IllegalDomainValueType.DOESNT_EXIST);
        }
    }

    public void setCodigoUnicoProduto(CodigoUnico codigoUnicoProduto) {
        this.codigoUnicoProduto = codigoUnicoProduto;
    }

    public void setQuantidadeAProduzir(double quantidadeAProduzir) throws IllegalDomainValueException {
        this.quantidadeAProduzir = new QuantidadeAProduzir(quantidadeAProduzir);
    }

    public void addIdentificadorEncomendas(String... identificadores) {
        for (String identificador : identificadores) {
            identificadoresEncomendas.add(new IdentificadorEncomenda(identificador));
        }
    }

    public boolean preparado() {
        return identificadorOrdemProducao != null && dataEmissao != null && dataPrevistaExecucao != null && codigoUnicoProduto != null
                && quantidadeAProduzir != null && identificadoresEncomendas.isEmpty();
    }

    public OrdemProducao build() throws IllegalDomainValueException {
        return new OrdemProducao(identificadorOrdemProducao, quantidadeAProduzir, new ArrayList<>(identificadoresEncomendas),
                dataEmissao, dataPrevistaExecucao, Estado.PENDENTE, codigoUnicoProduto);
    }
}
