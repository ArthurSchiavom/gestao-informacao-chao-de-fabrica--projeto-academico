package eapli.base.gestaoproducao.gestaomaquina.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrdemLinhaProducaoTest {

    @Test(expected = IllegalArgumentException.class)
    public void garantierOrdemMaiorQue0() {

        new OrdemLinhaProducao(0);
    }

    @Test
    public void garantierOrdemMaiorQue0_2() {
        OrdemLinhaProducao numero = new OrdemLinhaProducao(123456);
        assertTrue("Objeto criado", numero.ordemLinhaProducao == 123456);
    }
}