package eapli.base.gestaoproducao.gestaodeposito.domain;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DepositoTest {

	private final String DESCRICAO = "Descrição de exemplo";

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueCodigoNaoEstaNull() {
		new Deposito(null, DESCRICAO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueCodigoNaoEstaVazio() {
		new Deposito("", DESCRICAO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueCodigoNaoContemSomenteEspacos() {
		new Deposito("  ", DESCRICAO);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueCodigoNaoContemMaisQueUmaPalavra() {
		new Deposito("AS02 AS03", DESCRICAO);
	}

	@Test
	public void garantirDepositoComCodigoDescricao() {
		new Deposito("A01", "Deposito de Z");
		assertTrue(true);
	}

	@Test
	public void garantirQueNomeAtributoIdentidadeExiste() {
		boolean found = false;
		String identificador = Deposito.identityAttributeName();
		for (Field field : Deposito.class.getDeclaredFields()) {
			if (identificador.equals(field.getName()) &&
					field.getType().isAssignableFrom(CodigoDeposito.class)) {
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