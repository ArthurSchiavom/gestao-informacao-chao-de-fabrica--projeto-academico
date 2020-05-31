package Mensagens.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class CodigosTest {
	@Test
	public void searchForCodigo() {
		assertEquals(Codigos.searchForCodigo(1), Codigos.MSG);
		assertNull(Codigos.searchForCodigo(-1));
	}
}