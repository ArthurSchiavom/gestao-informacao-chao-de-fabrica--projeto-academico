package eapli.base.gestaoproducao.ordemProducao.application;

import eapli.base.gestaoproducao.gestaoproduto.application.dto.ProdutoDTO;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.domain.OrdemProducao;
import eapli.base.gestaoproducao.ordemProducao.repository.OrdemProducaoRepository;
import eapli.base.infrastructure.application.DTOUtils;
import eapli.base.infrastructure.application.EntityUtils;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.infrastructure.domain.IllegalDomainValueType;
import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.ConcurrencyException;

import javax.annotation.Nullable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EspecificarOrdemProducaoController {
    private final OrdemProducaoBuilder builder;
    private final List<ProdutoDTO> produtoDTOList;
    private final Map<String, Produto> codigoUnicoToProduto;
    private final RepositoryFactory repoFactory;

    public EspecificarOrdemProducaoController() {
        builder = new OrdemProducaoBuilder();

        repoFactory = PersistenceContext.repositories();
        ProdutoRepository produtoRepository = repoFactory.produto();
        List<Produto> produtos = produtoRepository.findAllList();
        produtoDTOList = DTOUtils.toDTOList(produtos);
        codigoUnicoToProduto = EntityUtils.mapIdStringToEntity(produtos);
    }

    public List<ProdutoDTO> produtos() {
        return produtoDTOList;
    }

    public void addIdentificadoresEncomenda(String... identificadores) {
        builder.addIdentificadorEncomendas(identificadores);
    }

    public void selecionarCodigoUnicoProduto(String codigo) throws IllegalDomainValueException {
        Produto produto = codigoUnicoToProduto.get(codigo);
        if (produto == null) {
            throw new IllegalDomainValueException("O código único de produto especificado não se encontra registado.", IllegalDomainValueType.DOESNT_EXIST);
        }

        builder.setCodigoUnicoProduto(produto.codigoUnico);
    }

    public void selecionarDataEmissao(@Nullable Date data) {
        if (data == null) {
            Calendar hoje = Calendar.getInstance();
            hoje.set(Calendar.MILLISECOND, 0);
            hoje.set(Calendar.SECOND, 0);
            hoje.set(Calendar.MINUTE, 0);
            hoje.set(Calendar.HOUR_OF_DAY, 0);

            data = new Date(hoje.getTimeInMillis());
        }
        builder.setDataEmissao(data);
    }

    public void selecionarDataPrevistaExecucao(Date data) throws IllegalDomainValueException {
        builder.setDataPrevistaExecucao(data);
    }

    public void selecionarIdentificadorOrdem(String id) throws IllegalDomainValueException {
        builder.setIdentificadorOrdemProducao(id, null);
    }

    public void selecionarQuantidadeAProduzir(double quantidade) throws IllegalDomainValueException {
        builder.setQuantidadeAProduzir(quantidade);
    }

    public void registar() throws IllegalDomainValueException, ConcurrencyException {
        OrdemProducao ordemProducao = builder.build();

        OrdemProducaoRepository repository = repoFactory.ordemProducao();
        repository.save(ordemProducao);
    }
}
