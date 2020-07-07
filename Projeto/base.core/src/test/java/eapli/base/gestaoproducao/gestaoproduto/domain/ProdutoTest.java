package eapli.base.gestaoproducao.gestaoproduto.domain;

import eapli.base.comum.domain.medicao.UnidadeDeMedida;
import eapli.base.gestaoproducao.gestaoproduto.application.dto.ProdutoDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import static eapli.base.Utils.assertUtils;
import static org.junit.Assert.*;

public class ProdutoTest {

    private static final CodigoUnico codigoUnico = Mockito.mock(CodigoUnico.class);
    private static final CodigoUnico codigoUnico2 = Mockito.mock(CodigoUnico.class);
    private static final CodigoComercial codigoComercial = Mockito.mock(CodigoComercial.class);
    private static final CategoriaDeProduto categoriaDeProduto = Mockito.mock(CategoriaDeProduto.class);
    private static final DescricaoBreve descricaoBreve = Mockito.mock(DescricaoBreve.class);
    private static final DescricaoCompleta descricaoCompleta = Mockito.mock(DescricaoCompleta.class);

    private static final String codigoUnicoValor = "1";
    private static final String codigoUnicoValor2 = "2";
    private static final String codigoComercialValor = "3";
    private static final String categoriaValor = "4";
    private static final String descricaoBreveValor = "5";
    private static final String descricaoCompletaValor = "6";

    @BeforeClass
    public static void startup() {
        ReflectionTestUtils.setField(codigoUnico, "codigoUnicoValor", codigoUnicoValor);
        ReflectionTestUtils.setField(codigoUnico2, "codigoUnicoValor", codigoUnicoValor2);
        ReflectionTestUtils.setField(codigoComercial, "codigoComercialValor", codigoComercialValor);
        ReflectionTestUtils.setField(categoriaDeProduto, "categoriaValor", categoriaValor);
        ReflectionTestUtils.setField(descricaoBreve, "descricaoBreveValor", descricaoBreveValor);
        ReflectionTestUtils.setField(descricaoCompleta, "descricaoCompletaValor", descricaoCompletaValor);
    }

    /**
     * Method: identityAttributeName()
     */
    @Test
    public void testIdentityAttributeName() throws Exception {
        assertEquals(Produto.identityAttributeName(), "codigoUnico");
    }

    /**
     * Method: podeGerarProduto(CodigoUnico codigoUnico, CategoriaDeProduto categoriaDeProduto, CodigoComercial codigoComercial, DescricaoBreve descricaoBreve, DescricaoCompleta descricaoCompleta, UnidadeDeMedida unidadeDeMedida)
     */
    @Test
    public void testPodeGerarProduto() throws Exception {
        assertTrue(Produto.podeGerarProduto(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.KILOGRAMA));
        assertFalse(Produto.podeGerarProduto(null, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.KILOGRAMA));
        assertFalse(Produto.podeGerarProduto(codigoUnico, null, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.KILOGRAMA));
        assertFalse(Produto.podeGerarProduto(codigoUnico, categoriaDeProduto, null, descricaoBreve, descricaoCompleta, UnidadeDeMedida.KILOGRAMA));
        assertFalse(Produto.podeGerarProduto(codigoUnico, categoriaDeProduto, codigoComercial, null, descricaoCompleta, UnidadeDeMedida.KILOGRAMA));
        assertFalse(Produto.podeGerarProduto(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, null, UnidadeDeMedida.KILOGRAMA));
        assertFalse(Produto.podeGerarProduto(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, null));
    }

    /**
     * Method: valueOf(CodigoUnico codigoUnico, CategoriaDeProduto categoriaDeProduto, CodigoComercial codigoComercial, DescricaoBreve descricaoBreve, DescricaoCompleta descricaoCompleta, UnidadeDeMedida unidadeDeMedida)
     */
    @Test
    public void testValueOf() throws Exception {
        assertUtils(IllegalArgumentException.class, ()-> Produto.valueOf(null, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.KILOGRAMA));
        assertUtils(IllegalArgumentException.class, ()-> Produto.valueOf(codigoUnico, null, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.KILOGRAMA));
        assertUtils(IllegalArgumentException.class, ()-> Produto.valueOf(codigoUnico, categoriaDeProduto, null, descricaoBreve, descricaoCompleta, UnidadeDeMedida.KILOGRAMA));
        assertUtils(IllegalArgumentException.class, ()-> Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, null, descricaoCompleta, UnidadeDeMedida.KILOGRAMA));
        assertUtils(IllegalArgumentException.class, ()-> Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, null, UnidadeDeMedida.KILOGRAMA));
        assertUtils(IllegalArgumentException.class, ()-> Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, null));

        Produto p = Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.GRAMA);
        assertNull(p.fichaDeProducao);
        assertEquals(p.codigoUnico, codigoUnico);
        assertEquals(p.categoriaDeProduto, categoriaDeProduto);
        assertEquals(p.codigoComercial, codigoComercial);
        assertEquals(p.descricaoBreve, descricaoBreve);
        assertEquals(p.descricaoCompleta, descricaoCompleta);
        assertEquals(p.unidadeDeMedida, UnidadeDeMedida.GRAMA);
    }

    /**
     * Method: equals(final Object o)
     */
    @Test
    public void testEquals() throws Exception {
        Produto p1 = Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.GRAMA);
        Produto p2 = Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.GRAMA);
        Produto p3 = Produto.valueOf(codigoUnico2, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.GRAMA);

        assertEquals(p1, p1);
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
    }

    /**
     * Method: hashCode()
     */
    @Test
    public void testHashCode() throws Exception {
        Produto p1 = Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.GRAMA);
        Produto p2 = Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.GRAMA);
        Produto p3 = Produto.valueOf(codigoUnico2, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.GRAMA);

        assertEquals(p1.hashCode(), p1.hashCode());
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
    }

    /**
     * Method: sameAs(final Object other)
     */
    @Test
    public void testSameAs() throws Exception {
        Produto p1 = Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.GRAMA);
        Produto p2 = Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.GRAMA);
        Produto p3 = Produto.valueOf(codigoUnico2, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.GRAMA);

        assertTrue(p1.sameAs(p1));
        assertTrue(p1.sameAs(p2));
        assertFalse(p1.sameAs(p3));
    }

    /**
     * Method: identity()
     */
    @Test
    public void testIdentity() throws Exception {
        Produto p1 = Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.GRAMA);
        assertEquals(p1.identity(), codigoUnico);
    }

    /**
     * Method: toDTO()
     */
    @Test
    public void testToDTO() throws Exception {
        Produto p1 = Produto.valueOf(codigoUnico, categoriaDeProduto, codigoComercial, descricaoBreve, descricaoCompleta, UnidadeDeMedida.GRAMA);

        ProdutoDTO produtoDTO = p1.toDTO();
        assertEquals(produtoDTO.codigoUnico, codigoUnicoValor);
        assertEquals(produtoDTO.codigoComercial, codigoComercialValor);
        assertEquals(produtoDTO.categoriaDeProduto, categoriaValor);
        assertEquals(produtoDTO.descricaoBreve, descricaoBreveValor);
        assertEquals(produtoDTO.descricaoCompleta, descricaoCompletaValor);
        assertEquals(produtoDTO.unidadeDeMedida, UnidadeDeMedida.GRAMA.abreviatura);
        assertNull(produtoDTO.fichaDeProducao);
    }


} 
