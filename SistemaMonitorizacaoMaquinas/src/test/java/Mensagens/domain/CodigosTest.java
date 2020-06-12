package Mensagens.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class CodigosTest {
	@Test
	public void searchForCodigo() {
		assertEquals(Codigos.MSG, Codigos.searchForCodigo(1));
		assertEquals(Codigos.ACK, Codigos.searchForCodigo(150));
		assertNull(Codigos.searchForCodigo(-1));
	}
}