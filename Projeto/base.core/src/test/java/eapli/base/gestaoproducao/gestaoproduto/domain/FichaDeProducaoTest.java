package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.gestaoproducao.gestaomateriaprima.domain.QuantidadeDeMateriaPrima;
import eapli.base.gestaoproducao.gestaoproduto.application.dto.FichaDeProducaoDTO;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import eapli.base.utilities.Lists;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static eapli.base.Utils.assertThrows;
import static org.junit.Assert.*;

/**
 * FichaDeProducao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 17, 2020</pre>
 */
public class FichaDeProducaoTest {

    private static List<QuantidadeDeMateriaPrima> listaGlobal;

    private static FichaDeProducao f1;
    private static FichaDeProducao f2;
    private static FichaDeProducao f3;

    private static final Integer v1 = 1;
    private static final Integer v2 = 1;
    private static final Integer v3 = 2;

    @BeforeClass
    public static void setUp() throws IllegalDomainValueException, NoSuchFieldException {
        QuantidadeDeMateriaPrima q1 = Mockito.mock(QuantidadeDeMateriaPrima.class);
        QuantidadeDeMateriaPrima q2 = Mockito.mock(QuantidadeDeMateriaPrima.class);
        QuantidadeDeMateriaPrima q3 = Mockito.mock(QuantidadeDeMateriaPrima.class);
        listaGlobal = new ArrayList<>();
        listaGlobal.add(q1);
        listaGlobal.add(q2);
        listaGlobal.add(q3);
        f1 = FichaDeProducao.valueOf(listaGlobal);
        f2 = FichaDeProducao.valueOf(listaGlobal);
        f3 = FichaDeProducao.valueOf(listaGlobal);

        ReflectionTestUtils.setField(f1, FichaDeProducao.identityAttributeName(), v1);
        ReflectionTestUtils.setField(f2, FichaDeProducao.identityAttributeName(), v2);
        ReflectionTestUtils.setField(f3, FichaDeProducao.identityAttributeName(), v3);
    }

    /**
     * Method: identityAttributeName()
     */
    @Test
    public void testIdentityAttributeName() {
        assertEquals(FichaDeProducao.identityAttributeName(), "uniqueVal");
    }

    /**
     * Method: valueOf(List<QuantidadeDeMateriaPrima> quantidadesDeMateriaPrima)
     */
    @Test
    public void testValueOf() throws IllegalDomainValueException {
        final List<QuantidadeDeMateriaPrima> lista = null;
        assertThrows(IllegalDomainValueException.class, () -> FichaDeProducao.valueOf(lista));
        final List<QuantidadeDeMateriaPrima> lista2 = new ArrayList<>();
        assertThrows(IllegalDomainValueException.class, () -> FichaDeProducao.valueOf(lista2));

        QuantidadeDeMateriaPrima q = Mockito.mock(QuantidadeDeMateriaPrima.class);
        lista2.add(q);
        lista2.add(null);
        FichaDeProducao fichaDeProducao = FichaDeProducao.valueOf(lista2);
        assertEquals(fichaDeProducao.quantidadesDeMateriaPrima.size(), 2);
        assertEquals(q, fichaDeProducao.quantidadesDeMateriaPrima.get(0));
    }

    /**
     * Method: equals(final Object o)
     */
    @Test
    public void testEquals() {
        assertEquals(f1, f1);
        assertEquals(f1, f2);
        assertNotEquals(f1, f3);
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() {
        assertEquals(f1.hashCode(), f1.hashCode());
        assertEquals(f1.hashCode(), f2.hashCode());
        assertNotEquals(f1.hashCode(), f3.hashCode());
    }

    /**
     * Method: sameAs(final Object other)
     */
    @Test
    public void testSameAs() {
        assertTrue(f1.sameAs(f1));
        assertTrue(f1.sameAs(f2));
        assertFalse(f1.sameAs(f3));
    }

    /**
     * Method: identity()
     */
    @Test
    public void testIdentity() {
        assertEquals(f1.identity(), v1);
        assertEquals(f2.identity(), v2);
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() {
        assertEquals(f1.toString(), Lists.generateColonSeparatedDisplayList(listaGlobal));
    }

    /**
     * Method: toDTO()
     */
    @Test
    public void testToDTO() {
        FichaDeProducaoDTO dto = f1.toDTO();
        assertEquals(dto.materiais.size(), listaGlobal.size());
    }

} 
