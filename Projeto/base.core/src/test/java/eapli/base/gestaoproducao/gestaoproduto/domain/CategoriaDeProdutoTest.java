package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.infrastructure.domain.IllegalDomainValueException;
import org.junit.Test;

import static eapli.base.Utils.assertThrows;
import static org.junit.Assert.*;

/**
 * CategoriaDeProduto Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 17, 2020</pre>
 */
public class CategoriaDeProdutoTest {

    /**
     * Method: valueOf(String categoria)
     */
    @Test
    public void testValueOf() throws Exception {
        CategoriaDeProduto categoriaDeProduto = CategoriaDeProduto.valueOf("hello");
        assertEquals(categoriaDeProduto.categoriaValor, "hello");

        assertThrows(IllegalArgumentException.class, () -> CategoriaDeProduto.valueOf(null));
        assertThrows(IllegalDomainValueException.class, () -> CategoriaDeProduto.valueOf(""));
    }

    /**
     * Method: equals(Object o)
     */
    @Test
    public void testEquals() throws Exception {
        CategoriaDeProduto cat1 = CategoriaDeProduto.valueOf("hello");
        CategoriaDeProduto cat2 = CategoriaDeProduto.valueOf("hello");
        CategoriaDeProduto cat3 = CategoriaDeProduto.valueOf("helo");

        assertEquals(cat1, cat2);
        assertNotEquals(cat1, cat3);
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() throws Exception {
        CategoriaDeProduto cat1 = CategoriaDeProduto.valueOf("hello");
        CategoriaDeProduto cat2 = CategoriaDeProduto.valueOf("hello");
        CategoriaDeProduto cat3 = CategoriaDeProduto.valueOf("helo");

        assertEquals(cat1.hashCode(), cat1.hashCode());
        assertEquals(cat1.hashCode(), cat2.hashCode());
        assertNotEquals(cat1.hashCode(), cat3.hashCode());
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws Exception {
        String nome = "yellow";
        CategoriaDeProduto cat1 = CategoriaDeProduto.valueOf(nome);
        assertEquals(cat1.toString(), nome);
    }

    /**
     * Method: compareTo(CategoriaDeProduto obj)
     */
    @Test
    public void testCompareTo() throws Exception {
        String first = "a_yellow";
        String second = "b_heyo";
        CategoriaDeProduto cat1 = CategoriaDeProduto.valueOf(first);
        CategoriaDeProduto cat2 = CategoriaDeProduto.valueOf(second);

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
