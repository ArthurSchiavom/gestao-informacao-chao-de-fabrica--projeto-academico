package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static eapli.base.Utils.assertUtils;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * CodigoComercial Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 17, 2020</pre>
 */
public class CodigoComercialTest {
    private static final Optional<Produto> optionalIsPresent = Optional.of(Mockito.mock(Produto.class));
    private static final Optional<Produto> optionalIsNotPresent = Optional.ofNullable(null);
    private static final ProdutoRepository produtoRepositoryIsPresent = Mockito.mock(ProdutoRepository.class);
    private static final ProdutoRepository produtoRepositoryIsNotPresent = Mockito.mock(ProdutoRepository.class);

    @BeforeClass
    public static void startup() {
        Mockito.when(produtoRepositoryIsPresent.produtoDeCodigoComercial(any())).thenReturn(optionalIsPresent);
        Mockito.when(produtoRepositoryIsNotPresent.produtoDeCodigoComercial(any())).thenReturn(optionalIsNotPresent);
    }

    /**
     * Method: valueOf(String codigoComercial, @Nullable ProdutoRepository repo)
     */
    @Test
    public void testValueOf() throws IllegalDomainValueException {
        assertUtils(IllegalDomainValueException.class, () -> CodigoComercial.valueOf("test", produtoRepositoryIsPresent));
        assertUtils(IllegalArgumentException.class, () -> CodigoComercial.valueOf(null, produtoRepositoryIsNotPresent));
        assertUtils(IllegalDomainValueException.class, () -> CodigoComercial.valueOf("", produtoRepositoryIsNotPresent));

        CodigoComercial c = CodigoComercial.valueOf("test", produtoRepositoryIsNotPresent);
        assertEquals(c.codigoComercialValor, "test");
    }

    /**
     * Method: equals(Object o)
     */
    @Test
    public void testEquals() throws IllegalDomainValueException {
        CodigoComercial cod1 = CodigoComercial.valueOf("hello", produtoRepositoryIsNotPresent);
        CodigoComercial cod2 = CodigoComercial.valueOf("hello", produtoRepositoryIsNotPresent);
        CodigoComercial cod3 = CodigoComercial.valueOf("helo", produtoRepositoryIsNotPresent);

        assertEquals(cod1, cod1);
        assertEquals(cod1, cod2);
        assertNotEquals(cod1, cod3);
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() throws IllegalDomainValueException {
        CodigoComercial cod1 = CodigoComercial.valueOf("hello", produtoRepositoryIsNotPresent);
        CodigoComercial cod2 = CodigoComercial.valueOf("hello", produtoRepositoryIsNotPresent);
        CodigoComercial cod3 = CodigoComercial.valueOf("helo", produtoRepositoryIsNotPresent);

        assertEquals(cod1.hashCode(), cod1.hashCode());
        assertEquals(cod1.hashCode(), cod2.hashCode());
        assertNotEquals(cod1.hashCode(), cod3.hashCode());
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws IllegalDomainValueException {
        CodigoComercial cod1 = CodigoComercial.valueOf("hello", produtoRepositoryIsNotPresent);
        assertEquals(cod1.toString(), "hello");
    }

    /**
     * Method: compareTo(CodigoComercial obj)
     */
    @Test
    public void testCompareTo() throws IllegalDomainValueException {
        String first = "a_yellow";
        String second = "b_heyo";
        CodigoComercial desc1 = CodigoComercial.valueOf(first, produtoRepositoryIsNotPresent);
        CodigoComercial desc2 = CodigoComercial.valueOf(second, produtoRepositoryIsNotPresent);

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
