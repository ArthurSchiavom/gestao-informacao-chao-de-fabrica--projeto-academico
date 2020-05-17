package eapli.base.gestaoproducao.medicao;

import eapli.base.infrastructure.domain.IllegalDomainValueException;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static eapli.base.Utils.assertThrows;
import static org.junit.Assert.assertEquals;

/**
 * UnidadeDeMedida Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 17, 2020</pre>
 */
public class UnidadeDeMedidaTest {

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws Exception {
        assertEquals(UnidadeDeMedida.KILOGRAMA.toString(), "Kg");
    }

    /**
     * Method: actualValueOf(String identificador)
     */
    @Test
    public void testActualValueOf() throws Exception {
        assertEquals(UnidadeDeMedida.actualValueOf("cm"), UnidadeDeMedida.CENTIMETRO);
        assertEquals(UnidadeDeMedida.actualValueOf("centímetros"), UnidadeDeMedida.CENTIMETRO);
        assertEquals(UnidadeDeMedida.actualValueOf("unidades"), UnidadeDeMedida.UNIDADES);
        assertThrows(IllegalDomainValueException.class, () -> UnidadeDeMedida.actualValueOf("inexistente"));
    }
} 
