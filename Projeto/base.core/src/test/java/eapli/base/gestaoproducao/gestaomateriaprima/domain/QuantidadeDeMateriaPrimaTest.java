package eapli.base.gestaoproducao.gestaomateriaprima.domain;

import eapli.base.comum.domain.medicao.QuantidadePositiva;
import eapli.base.gestaoproducao.gestaoproduto.application.dto.QuantidadeDeMateriaPrimaDTO;
import org.junit.Test;

import static eapli.base.Utils.assertUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * QuantidadeDeMateriaPrima Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 17, 2020</pre>
 */
public class QuantidadeDeMateriaPrimaTest {

    /**
     * Method: valueOf(QuantidadePositiva quantidade, MateriaPrima materiaPrima)
     */
    @Test
    public void testValueOf() throws Exception {
        QuantidadeDeMateriaPrima quantidadeDeMateriaPrima =
                QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(1), MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "a"));

        assertEquals(1, quantidadeDeMateriaPrima.quantidade.quantidadeValor, 0.00001);
        assertEquals("a", quantidadeDeMateriaPrima.materiaPrima.idMateria);
        assertEquals(TipoDeMateriaPrima.MATERIAL, quantidadeDeMateriaPrima.materiaPrima.tipoDeMateriaPrima);

        assertUtils(IllegalArgumentException.class, () ->
                QuantidadeDeMateriaPrima.valueOf(null, MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "a")));
        assertUtils(IllegalArgumentException.class, () ->
                QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(1), null));
    }

    /**
     * Method: equals(Object o)
     */
    @Test
    public void testEquals() throws Exception {
        QuantidadeDeMateriaPrima q1 = QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(1), MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "a"));
        QuantidadeDeMateriaPrima q2 = QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(1), MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "a"));
        QuantidadeDeMateriaPrima q3 = QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(1), MateriaPrima.valueOf(TipoDeMateriaPrima.PRODUTO, "a"));
        QuantidadeDeMateriaPrima q4 = QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(2), MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "a"));

        assertEquals(q1, q1);
        assertEquals(q1, q2);
        assertNotEquals(q1, q3);
        assertNotEquals(q1, q4);
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() throws Exception {
        QuantidadeDeMateriaPrima q1 = QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(1), MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "a"));
        QuantidadeDeMateriaPrima q2 = QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(1), MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "a"));
        QuantidadeDeMateriaPrima q3 = QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(1), MateriaPrima.valueOf(TipoDeMateriaPrima.PRODUTO, "a"));
        QuantidadeDeMateriaPrima q4 = QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(2), MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "a"));

        assertEquals(q1.hashCode(), q1.hashCode());
        assertEquals(q1.hashCode(), q2.hashCode());
        assertNotEquals(q1.hashCode(), q3.hashCode());
        assertNotEquals(q1.hashCode(), q4.hashCode());
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws Exception {
        QuantidadeDeMateriaPrima q1 = QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(3), MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "a"));
        assertEquals("3,0000 de a (Material)", q1.toString());
    }

    /**
     * Method: compareTo(QuantidadeDeMateriaPrima obj)
     */
    @Test
    public void testCompareTo() throws Exception {
        QuantidadeDeMateriaPrima q1 = QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(3), MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "a"));
        assertUtils(UnsupportedOperationException.class, () -> q1.compareTo(q1));
    }

    /**
     * Method: toDTO()
     */
    @Test
    public void testToDTO() throws Exception {
        QuantidadeDeMateriaPrima q1 = QuantidadeDeMateriaPrima.valueOf(QuantidadePositiva.valueOf(3), MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "ab"));
        QuantidadeDeMateriaPrimaDTO d1 = q1.toDTO();
        assertEquals(3d, d1.quantidade, 0.00001);
        assertEquals(TipoDeMateriaPrima.MATERIAL.toString() , d1.materiaPrimaDTO.tipoDeMateriaPrima);
        assertEquals("ab" , d1.materiaPrimaDTO.idMateria);
    }
} 
