package eapli.base.gestaoproducao.gestaolinhasproducao.domain;

import eapli.base.gestaoproducao.exportacao.domain.ChaoDeFabrica;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.lang.reflect.Field;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
	public void garantirLinhaProducaoComIdentificador() {
		new LinhaProducao("Linha Produção 01");
		assertTrue(true);
	}

	@Test
	public void garantirNomeAtributoIdentidadeExiste() {
		boolean found = false;
		String identificador = LinhaProducao.identityAttributeName();
		for (Field field : LinhaProducao.class.getDeclaredFields()) {
			if (identificador.equals(field.getName()) &&
					field.getType().isAssignableFrom(IdentificadorLinhaProducao.class)) {
				found = true;
				break;
			}
		}
		if (found) {
			assertTrue(true);
		} else {
			fail("Verificar valor de retorno do metodo identityAttributeName");
		}
	}
}