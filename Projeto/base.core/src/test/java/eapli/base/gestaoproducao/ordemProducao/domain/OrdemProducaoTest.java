package eapli.base.gestaoproducao.ordemProducao.domain;

import eapli.base.gestaoproducao.gestaoproduto.application.especificacao.ResultadoImportacaoLinhaALinha;
import eapli.base.gestaoproducao.gestaoproduto.domain.CodigoUnico;
import eapli.base.gestaoproducao.gestaoproduto.domain.Produto;
import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.gestaoproducao.ordemProducao.application.ImportarOrdensProducaoController;
import eapli.base.gestaoproducao.ordemProducao.application.OrdemProducaoDTO;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class OrdemProducaoTest {


    private static final Optional<Produto> optionalIsPresent = Optional.of(Mockito.mock(Produto.class));
    private static final Optional<Produto> optionalIsNotPresent = Optional.ofNullable(null);
    private static final ProdutoRepository produtoRepositoryIsPresent = Mockito.mock(ProdutoRepository.class);
    private static final ProdutoRepository produtoRepositoryIsNotPresent = Mockito.mock(ProdutoRepository.class);

    @BeforeClass
    public static void startup() {
        Mockito.when(produtoRepositoryIsPresent.produtoDeCodigoUnico(any())).thenReturn(optionalIsPresent);
        Mockito.when(produtoRepositoryIsNotPresent.produtoDeCodigoUnico(any())).thenReturn(optionalIsNotPresent);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorDataNoFuturo() throws IllegalDomainValueException {
        List<IdentificadorEncomenda> encomendas = new ArrayList<>();
        encomendas.add(new IdentificadorEncomenda("123"));
        OrdemProducao op = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(123), encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        OrdemProducao op1 = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(1234),
                encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        OrdemProducao op2 = new OrdemProducao(new Identificador("dake"), new QuantidadeAProduzir(123),
                encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String s = "12-12-2030";
        Date data;

        try {
            data = sdf.parse(s);
            OrdemProducao op5 = new OrdemProducao(new Identificador("dake"), new QuantidadeAProduzir(123),
                    new ArrayList<>(),
                    data,
                    new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));

        } catch (ParseException e) {
        }
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructorQuantidadeNegativa() throws IllegalDomainValueException {
        List<IdentificadorEncomenda> encomendas = new ArrayList<>();
        encomendas.add(new IdentificadorEncomenda("123"));
        OrdemProducao op = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(123), encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        OrdemProducao op1 = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(1234),
                encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        OrdemProducao op2 = new OrdemProducao(new Identificador("dake"), new QuantidadeAProduzir(123),
                encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String s = "12-12-2030";
        Date data;

        try {
            data = sdf.parse(s);
            OrdemProducao op5 = new OrdemProducao(new Identificador("dake"), new QuantidadeAProduzir(-1),
                    new ArrayList<>(),
                    new Date(),
                    new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));

        } catch (ParseException e) {
        }
    }

    @Test
    public void testConstructorDataCorreta() throws IllegalDomainValueException {
        List<IdentificadorEncomenda> encomendas = new ArrayList<>();
        encomendas.add(new IdentificadorEncomenda("123"));
        OrdemProducao op = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(123), encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        OrdemProducao op1 = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(1234),
                encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        OrdemProducao op2 = new OrdemProducao(new Identificador("dake"), new QuantidadeAProduzir(123),
                encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String s = "12-12-2030";
        Date data;

        try {
            data = sdf.parse(s);
            OrdemProducao op5 = new OrdemProducao(new Identificador("dake"), new QuantidadeAProduzir(123),
                    encomendas,
                    new Date(),
                    data, Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));

        } catch (ParseException e) {
        }
    }

    @Test
    public void identityAttributeName() {
        assertEquals("identificador", OrdemProducao.identityAttributeName());
    }

    @Test
    public void identity() throws IllegalDomainValueException {
        List<IdentificadorEncomenda> encomendas = new ArrayList<>();
        encomendas.add(new IdentificadorEncomenda("123"));
        OrdemProducao op = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(123), encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO,CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        OrdemProducao op1 = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(1234),
                encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        OrdemProducao op2 = new OrdemProducao(new Identificador("dake"), new QuantidadeAProduzir(123),
                encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        assertEquals(op1.identity(), new Identificador("dak"));
    }

    @Test
    public void gerarOrdensProducaoDTO() throws IllegalDomainValueException {
        List<IdentificadorEncomenda> encomendas = new ArrayList<>();
        encomendas.add(new IdentificadorEncomenda("123"));
        OrdemProducao op = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(123), encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        OrdemProducao op1 = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(1234),
                encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        OrdemProducao op2 = new OrdemProducao(new Identificador("dake"), new QuantidadeAProduzir(123),
                encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        assertTrue(OrdemProducao.gerarOrdensProducaoDTO(op).information.equals(new OrdemProducaoDTO(op).information));
    }

    @Test
    public void testGerarOrdensProducaoDTO() throws IllegalDomainValueException {
        List<IdentificadorEncomenda> encomendas = new ArrayList<>();
        encomendas.add(new IdentificadorEncomenda("123"));
        OrdemProducao op = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(123), encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        OrdemProducao op1 = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(1234),
                encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        OrdemProducao op2 = new OrdemProducao(new Identificador("dake"), new QuantidadeAProduzir(123),
                encomendas,
                new Date(),
                new Date(), Estado.EM_EXECUCAO, CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent));
        List<OrdemProducao> ordens = new ArrayList<>();
        ordens.add(op);
        ordens.add(op1);
        ordens.add(op2);

        List<OrdemProducaoDTO> opDTO = OrdemProducao.gerarOrdensProducaoDTO(ordens);
        List<OrdemProducaoDTO> ordensDTO = new ArrayList<>();
        ordensDTO.add(OrdemProducao.gerarOrdensProducaoDTO(op));
        ordensDTO.add(OrdemProducao.gerarOrdensProducaoDTO(op1));
        ordensDTO.add(OrdemProducao.gerarOrdensProducaoDTO(op2));

        int i = 0;
        for (OrdemProducaoDTO o : ordensDTO) {
            assertEquals(ordensDTO.get(i).information, opDTO.get(i).information);
            i++;
        }
    }
}