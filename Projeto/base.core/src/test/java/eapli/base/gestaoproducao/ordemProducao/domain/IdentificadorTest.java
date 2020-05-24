package eapli.base.gestaoproducao.ordemProducao.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class IdentificadorTest {

    @Test
    public void compareTo() {
        Identificador id1 = new Identificador("teste001");
        Identificador id2 = new Identificador("teste001");
        Identificador id3 = new Identificador("teste002");

        assertTrue(id1.compareTo(id2) == 0);
        assertTrue(id1.compareTo(id3) < 0);

    }
}