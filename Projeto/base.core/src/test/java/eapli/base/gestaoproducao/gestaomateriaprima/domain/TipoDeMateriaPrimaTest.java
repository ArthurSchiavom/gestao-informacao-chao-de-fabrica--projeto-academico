package eapli.base.gestaoproducao.gestaomateriaprima.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TipoDeMateriaPrima Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 17, 2020</pre>
 */
public class TipoDeMateriaPrimaTest {

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws Exception {
        assertEquals(TipoDeMateriaPrima.PRODUTO.toString(), "Produto");
        assertEquals(TipoDeMateriaPrima.MATERIAL.toString(), "Material");
    }
} 
