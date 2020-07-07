package eapli.base.gestaoproducao.ordemProducao.domain;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class IdentificadorOrdemProducaoTest {

    @Test
    public void compareTo() {
        IdentificadorOrdemProducao id1 = new IdentificadorOrdemProducao("teste001");
        IdentificadorOrdemProducao id2 = new IdentificadorOrdemProducao("teste001");
        IdentificadorOrdemProducao id3 = new IdentificadorOrdemProducao("teste002");

        assertTrue(id1.compareTo(id2) == 0);
        assertTrue(id1.compareTo(id3) < 0);

    }
}