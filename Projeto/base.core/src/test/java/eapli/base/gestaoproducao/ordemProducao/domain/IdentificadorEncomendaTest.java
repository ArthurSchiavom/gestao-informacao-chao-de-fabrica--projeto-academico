package eapli.base.gestaoproducao.ordemProducao.domain;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class IdentificadorEncomendaTest {

    @Test
    public void compareTo() {

        IdentificadorEncomenda id1 = new IdentificadorEncomenda("123");
        IdentificadorEncomenda id2 = new IdentificadorEncomenda("1234");
        IdentificadorEncomenda id3 = new IdentificadorEncomenda("123");

        assertTrue(id1.compareTo(id3)==0);
        assertTrue(id3.compareTo(id2)<0);
    }
}