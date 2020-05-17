package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.infrastructure.domain.IllegalDomainValueException;
import org.junit.Test;

import static eapli.base.Utils.assertThrows;
import static org.junit.Assert.*;

/**
 * DescricaoBreve Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 17, 2020</pre>
 */
public class DescricaoBreveTest {

    /**
     * Method: valueOf(String descricaoBreve)
     */
    @Test
    public void testValueOf() throws IllegalDomainValueException {
        assertThrows(IllegalDomainValueException.class, () -> DescricaoBreve.valueOf("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")); //31 caracteres
        assertThrows(IllegalDomainValueException.class, () -> DescricaoBreve.valueOf(""));

        String valor = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        DescricaoBreve desc = DescricaoBreve.valueOf(valor); // 30 caracteres
        assertEquals(desc.descricaoBreveValor, valor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOf2() throws IllegalArgumentException, IllegalDomainValueException {
        DescricaoBreve.valueOf(null);
    }

    /**
     * Method: equals(Object o)
     */
    @Test
    public void testEquals() throws Exception {
        String valor = "baby blue";
        String valor2 = "light blue";
        DescricaoBreve desc1 = DescricaoBreve.valueOf(valor);
        DescricaoBreve desc2 = DescricaoBreve.valueOf(valor);
        DescricaoBreve desc3 = DescricaoBreve.valueOf(valor2);
        assertEquals(desc1, desc1);
        assertEquals(desc1, desc2);
        assertNotEquals(desc1, desc3);
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() throws Exception {
        String valor = "baby blue";
        String valor2 = "light blue";
        DescricaoBreve desc1 = DescricaoBreve.valueOf(valor);
        DescricaoBreve desc2 = DescricaoBreve.valueOf(valor);
        DescricaoBreve desc3 = DescricaoBreve.valueOf(valor2);
        assertEquals(desc1.hashCode(), desc1.hashCode());
        assertEquals(desc1.hashCode(), desc2.hashCode());
        assertNotEquals(desc1.hashCode(), desc3.hashCode());
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws Exception {
        String valor = "light blue";
        DescricaoBreve desc1 = DescricaoBreve.valueOf(valor);
        assertEquals("light blue", desc1.descricaoBreveValor);
    }

    /**
     * Method: compareTo(DescricaoBreve obj)
     */
    @Test
    public void testCompareTo() throws Exception {
        String first = "a_yellow";
        String second = "b_heyo";
        DescricaoBreve cat1 = DescricaoBreve.valueOf(first);
        DescricaoBreve cat2 = DescricaoBreve.valueOf(second);

        int r1 = cat1.compareTo(cat2);
        int r2 = cat2.compareTo(cat1);
        int r3 = cat1.compareTo(cat1);
        assertTrue(r1 < 0);
        assertTrue(r2 > 0);
        assertTrue(r3 == 0);
        assertEquals(r1, first.compareTo(second));
        assertEquals(r2, second.compareTo(first));
        assertEquals(r3, first.compareTo(first));
    }


} 
