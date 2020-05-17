package eapli.base.gestaoproducao.gestaomateriaprima.domain;

import eapli.base.gestaoproducao.gestaomateriaprima.application.dto.MateriaPrimaDTO;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import org.junit.Test;

import static eapli.base.Utils.assertThrows;
import static org.junit.Assert.*;

/**
 * MateriaPrima Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 17, 2020</pre>
 */
public class MateriaPrimaTest {

    /**
     * Method: getSerialVersionUID()
     */
    @Test
    public void testGetSerialVersionUID() throws Exception {
        MateriaPrima materiaPrima = MateriaPrima.valueOf(TipoDeMateriaPrima.PRODUTO, "sunny");
        assertEquals(1L, materiaPrima.getSerialVersionUID());
    }

    /**
     * Method: valueOf(TipoDeMateriaPrima tipoDeMateriaPrima, String idMateria)
     */
    @Test
    public void testValueOf() throws Exception {
        MateriaPrima materiaPrima = MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "sunny");
        assertEquals(materiaPrima.idMateria, "sunny");
        assertEquals(materiaPrima.tipoDeMateriaPrima, TipoDeMateriaPrima.MATERIAL);

        assertThrows(IllegalArgumentException.class, () -> MateriaPrima.valueOf(null, "sunny"));
        assertThrows(IllegalArgumentException.class, () -> MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, null));
        assertThrows(IllegalDomainValueException.class, () -> MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, ""));
    }

    /**
     * Method: toDTO()
     */
    @Test
    public void testToDTO() throws Exception {
        MateriaPrimaDTO materiaPrimaDTO = MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "sunny").toDTO();
        assertEquals(materiaPrimaDTO.idMateria, "sunny");
        assertEquals(materiaPrimaDTO.tipoDeMateriaPrima, TipoDeMateriaPrima.MATERIAL.name);


        MateriaPrimaDTO materiaPrimaDTO2 = MateriaPrima.valueOf(TipoDeMateriaPrima.PRODUTO, "sunny").toDTO();
        assertEquals(materiaPrimaDTO2.tipoDeMateriaPrima, TipoDeMateriaPrima.PRODUTO.name);
    }

    /**
     * Method: equals(Object o)
     */
    @Test
    public void testEquals() throws Exception {
        MateriaPrima mat1 = MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "hello");
        MateriaPrima mat2 = MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "hello");
        MateriaPrima mat3 = MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "hellow");
        MateriaPrima mat4 = MateriaPrima.valueOf(TipoDeMateriaPrima.PRODUTO, "hello");

        assertEquals(mat1, mat1);
        assertEquals(mat1, mat2);
        assertNotEquals(mat1, mat3);
        assertNotEquals(mat1, mat4);
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() throws Exception {
        MateriaPrima mat1 = MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "hello");
        MateriaPrima mat2 = MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "hello");
        MateriaPrima mat3 = MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "hellow");
        MateriaPrima mat4 = MateriaPrima.valueOf(TipoDeMateriaPrima.PRODUTO, "hello");

        assertEquals(mat1.hashCode(), mat1.hashCode());
        assertEquals(mat1.hashCode(), mat2.hashCode());
        assertNotEquals(mat1.hashCode(), mat3.hashCode());
        assertNotEquals(mat1.hashCode(), mat4.hashCode());
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws Exception {
        MateriaPrima mat1 = MateriaPrima.valueOf(TipoDeMateriaPrima.MATERIAL, "hello");
        MateriaPrima mat2 = MateriaPrima.valueOf(TipoDeMateriaPrima.PRODUTO, "pool");
        assertEquals("hello (Material)", mat1.toString());
        assertEquals("pool (Produto)", mat2.toString());
    }
} 
