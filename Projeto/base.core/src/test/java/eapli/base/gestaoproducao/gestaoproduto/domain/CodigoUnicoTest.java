package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.gestaoproducao.gestaoproduto.persistence.ProdutoRepository;
import eapli.base.infrastructure.domain.IllegalDomainValueException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static eapli.base.Utils.assertThrows;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * CodigoUnico Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>mai 17, 2020</pre>
 */
public class CodigoUnicoTest {
    private static final Optional<Produto> optionalIsPresent = Optional.of(Mockito.mock(Produto.class));
    private static final Optional<Produto> optionalIsNotPresent = Optional.ofNullable(null);
    private static final ProdutoRepository produtoRepositoryIsPresent = Mockito.mock(ProdutoRepository.class);
    private static final ProdutoRepository produtoRepositoryIsNotPresent = Mockito.mock(ProdutoRepository.class);

    @BeforeClass
    public static void startup() {
        Mockito.when(produtoRepositoryIsPresent.produtoDeCodigoUnico(any())).thenReturn(optionalIsPresent);
        Mockito.when(produtoRepositoryIsNotPresent.produtoDeCodigoUnico(any())).thenReturn(optionalIsNotPresent);
    }
    
    /**
     * Method: valueOf(String codigoUnico, @Nullable ProdutoRepository repo)
     */
    @Test
    public void testValueOf() throws IllegalDomainValueException {
        assertThrows(IllegalDomainValueException.class, () -> CodigoUnico.valueOf("test", produtoRepositoryIsPresent));
        assertThrows(IllegalArgumentException.class, () -> CodigoUnico.valueOf(null, produtoRepositoryIsNotPresent));
        assertThrows(IllegalDomainValueException.class, () -> CodigoUnico.valueOf("", produtoRepositoryIsNotPresent));

        CodigoUnico c = CodigoUnico.valueOf("test", produtoRepositoryIsNotPresent);
        assertEquals(c.codigoUnicoValor, "test");
    }

    /**
     * Method: equals(Object o)
     */
    @Test
    public void testEquals() throws IllegalDomainValueException {
        CodigoUnico cod1 = CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent);
        CodigoUnico cod2 = CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent);
        CodigoUnico cod3 = CodigoUnico.valueOf("helo", produtoRepositoryIsNotPresent);

        assertEquals(cod1, cod1);
        assertEquals(cod1, cod2);
        assertNotEquals(cod1, cod3);
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() throws IllegalDomainValueException {
        CodigoUnico cod1 = CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent);
        CodigoUnico cod2 = CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent);
        CodigoUnico cod3 = CodigoUnico.valueOf("helo", produtoRepositoryIsNotPresent);

        assertEquals(cod1.hashCode(), cod1.hashCode());
        assertEquals(cod1.hashCode(), cod2.hashCode());
        assertNotEquals(cod1.hashCode(), cod3.hashCode());
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws IllegalDomainValueException {
        CodigoUnico cod1 = CodigoUnico.valueOf("hello", produtoRepositoryIsNotPresent);
        assertEquals(cod1.toString(), "hello");
    }

    /**
     * Method: compareTo(CodigoUnico obj)
     */
    @Test
    public void testCompareTo() throws IllegalDomainValueException {
        String first = "a_yellow";
        String second = "b_heyo";
        CodigoUnico desc1 = CodigoUnico.valueOf(first, produtoRepositoryIsNotPresent);
        CodigoUnico desc2 = CodigoUnico.valueOf(second, produtoRepositoryIsNotPresent);

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
