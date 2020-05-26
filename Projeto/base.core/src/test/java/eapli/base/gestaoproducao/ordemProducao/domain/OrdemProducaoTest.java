package eapli.base.gestaoproducao.ordemProducao.domain;

import eapli.base.gestaoproducao.gestaoproduto.application.especificacao.ResultadoImportacaoLinhaALinha;
import eapli.base.gestaoproducao.ordemProducao.application.ImportarOrdensProducaoController;
import eapli.base.gestaoproducao.ordemProducao.application.OrdemProducaoDTO;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class OrdemProducaoTest {

    OrdemProducao op = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(123), new ArrayList<>(),
            new Date(),
            new Date(), Estado.EM_EXECUCAO);
    OrdemProducao op1 = new OrdemProducao(new Identificador("dak"), new QuantidadeAProduzir(1234), new ArrayList<>(),
            new Date(),
            new Date(), Estado.EM_EXECUCAO);
    OrdemProducao op2 = new OrdemProducao(new Identificador("dake"), new QuantidadeAProduzir(123), new ArrayList<>(),
            new Date(),
            new Date(), Estado.EM_EXECUCAO);


    @Test(expected = IllegalArgumentException.class)
    public void testConstructorDataNoFuturo() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String s = "12-12-2030";
        Date data;

        try {
            data = sdf.parse(s);
            OrdemProducao op5 = new OrdemProducao(new Identificador("dake"), new QuantidadeAProduzir(123), new ArrayList<>(),
                    data,
                    new Date(), Estado.EM_EXECUCAO);

        } catch (ParseException e) {
        }
    }


    @Test(expected = IllegalArgumentException.class)
    public void testConstructorQuantidadeNegativa() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String s = "12-12-2030";
        Date data;

        try {
            data = sdf.parse(s);
            OrdemProducao op5 = new OrdemProducao(new Identificador("dake"), new QuantidadeAProduzir(-1), new ArrayList<>(),
                    new Date(),
                    new Date(), Estado.EM_EXECUCAO);

        } catch (ParseException e) {
        }
    }

    @Test
    public void testConstructorDataCorreta() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String s = "12-12-2030";
        Date data;

        try {
            data = sdf.parse(s);
            OrdemProducao op5 = new OrdemProducao(new Identificador("dake"), new QuantidadeAProduzir(123), new ArrayList<>(),
                    new Date(),
                    data, Estado.EM_EXECUCAO);

        } catch (ParseException e) {
        }
    }

    @Test
    public void identityAttributeName() {
        assertEquals("identificador", OrdemProducao.identityAttributeName());
    }

    @Test
    public void identity() {
        assertEquals(op1.identity(), new Identificador("dak"));
    }

    @Test
    public void gerarOrdensProducaoDTO() {
        assertTrue(OrdemProducao.gerarOrdensProducaoDTO(op).information.equals(new OrdemProducaoDTO(op).information));
    }

    @Test
    public void testGerarOrdensProducaoDTO() {
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