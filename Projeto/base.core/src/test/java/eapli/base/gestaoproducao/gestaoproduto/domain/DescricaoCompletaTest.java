package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.infrastructure.domain.IllegalDomainValueException;
import org.junit.Test;

import static eapli.base.Utils.assertThrows;
import static org.junit.Assert.*;

/** 
* DescricaoCompleta Tester. 
* 
* @author <Authors name> 
* @since <pre>mai 17, 2020</pre> 
* @version 1.0 
*/ 
public class DescricaoCompletaTest {
    
    /**
     * Method: valueOf(String descricao)
     */
    @Test
    public void testValueOf() throws Exception {
        DescricaoCompleta desc = DescricaoCompleta.valueOf("hello");
        assertEquals(desc.descricaoCompletaValor, "hello");

        assertThrows(IllegalDomainValueException.class, () -> DescricaoCompleta.valueOf(""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValueOf2() throws Exception {
        DescricaoCompleta.valueOf(null);
    }

    /**
     * Method: equals(Object o)
     */
    @Test
    public void testEquals() throws Exception {
        DescricaoCompleta desc1 = DescricaoCompleta.valueOf("hello");
        DescricaoCompleta desc2 = DescricaoCompleta.valueOf("hello");
        DescricaoCompleta desc3 = DescricaoCompleta.valueOf("helo");

        assertEquals(desc1, desc1);
        assertEquals(desc1, desc2);
        assertNotEquals(desc1, desc3);
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() throws Exception {
        DescricaoCompleta desc1 = DescricaoCompleta.valueOf("hello");
        DescricaoCompleta desc2 = DescricaoCompleta.valueOf("hello");
        DescricaoCompleta desc3 = DescricaoCompleta.valueOf("helo");

        assertEquals(desc1.hashCode(), desc1.hashCode());
        assertEquals(desc1.hashCode(), desc2.hashCode());
        assertNotEquals(desc1.hashCode(), desc3.hashCode());
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws Exception {
        String nome = "yellow";
        DescricaoCompleta desc1 = DescricaoCompleta.valueOf(nome);
        assertEquals(desc1.toString(), nome);
    }

    /**
     * Method: compareTo(DescricaoCompleta obj)
     */
    @Test
    public void testCompareTo() throws Exception {
        String first = "a_yellow";
        String second = "b_heyo";
        DescricaoCompleta desc1 = DescricaoCompleta.valueOf(first);
        DescricaoCompleta desc2 = DescricaoCompleta.valueOf(second);

        int r1 = desc1.compareTo(desc2);
        int r2 = desc2.compareTo(desc1);
        int r3 = desc1.compareTo(desc1);
        assertTrue(r1 < 0);
        assertTrue(r2 > 0);
        assertTrue(r3 == 0);
        assertEquals(r1, first.compareTo(second));
        assertEquals(r2, second.compareTo(first));
        assertEquals(r3, first.compareTo(first));
    }



} 
