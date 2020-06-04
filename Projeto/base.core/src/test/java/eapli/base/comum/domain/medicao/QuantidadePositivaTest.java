package eapli.base.comum.domain.medicao;

import eapli.base.infrastructure.domain.IllegalDomainValueException;
import org.junit.Test;

import static eapli.base.Utils.assertUtils;
import static org.junit.Assert.*;

/**
 * QuantidadePositiva Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 17, 2020</pre>
 */
public class QuantidadePositivaTest {

    /**
     * Method: valueOf(double quantidade)
     */
    @Test
    public void testValueOf() throws Exception {
        QuantidadePositiva q1 = QuantidadePositiva.valueOf(3.5d);
        assertEquals(3.5d, q1.quantidadeValor, 0.0001d);

        assertUtils(IllegalDomainValueException.class, () -> QuantidadePositiva.valueOf(0d));
        assertUtils(IllegalDomainValueException.class, () -> QuantidadePositiva.valueOf(-10d));
    }

    /**
     * Method: equals(Object o)
     */
    @Test
    public void testEquals() throws Exception {
        QuantidadePositiva q1 = QuantidadePositiva.valueOf(1d);
        QuantidadePositiva q2 = QuantidadePositiva.valueOf(1d);
        QuantidadePositiva q3 = QuantidadePositiva.valueOf(2d);

        assertEquals(q1, q1);
        assertEquals(q1, q2);
        assertNotEquals(q1, q3);
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() throws Exception {
        QuantidadePositiva q1 = QuantidadePositiva.valueOf(1d);
        QuantidadePositiva q2 = QuantidadePositiva.valueOf(1d);
        QuantidadePositiva q3 = QuantidadePositiva.valueOf(2d);

        assertEquals(q1.hashCode(), q1.hashCode());
        assertEquals(q1.hashCode(), q2.hashCode());
        assertNotEquals(q1.hashCode(), q3.hashCode());
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws Exception {
        QuantidadePositiva q3 = QuantidadePositiva.valueOf(2d);
        assertEquals("2,0000", q3.toString());
    }

    /**
     * Method: compareTo(QuantidadePositiva obj)
     */
    @Test
    public void testCompareTo() throws Exception {
        QuantidadePositiva q1 = QuantidadePositiva.valueOf(1d);
        QuantidadePositiva q2 = QuantidadePositiva.valueOf(2d);

        int r1 = q1.compareTo(q2);
        int r2 = q2.compareTo(q1);
        int r3 = q1.compareTo(q1);
        assertTrue(r1 < 0);
        assertTrue(r2 > 0);
        assertTrue(r3 == 0);
    }


} 
