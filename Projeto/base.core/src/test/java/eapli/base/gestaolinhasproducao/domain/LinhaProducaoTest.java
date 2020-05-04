package eapli.base.gestaolinhasproducao.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import java.lang.reflect.Field;

public class LinhaProducaoTest {
	@Test(expected = IllegalArgumentException.class)
	public void garantirIdentificadorNaoPodeSerNull() {
		new LinhaProducao(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirIdentificadorNaoPodeSerStringVazia() {
		new LinhaProducao("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirIdentificadorNaoPodeSerStringVaziaComEspacos() {
		new LinhaProducao(" ");
	}

	@Test
	public void garantirNomeAtributoIdentidadeExiste() {
		String identificador = LinhaProducao.identityAttributeName();
		for (Field field : LinhaProducao.class.getDeclaredFields()) {
			if (identificador.equals(field.getName())) {
				assertTrue(true);
			}
		}
		fail();
	}
}