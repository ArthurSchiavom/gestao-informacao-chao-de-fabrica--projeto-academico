package Mensagens.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class VersionTest {
	@Test(expected = IllegalArgumentException.class)
	public void garantirQueVersaoNaoEMaiorQue0() {
		new Version(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueVersaoEMenorQue255() {
		new Version(265);
	}

	@Test
	public void incrementar() {
		Version version = new Version(255);
		assertEquals(version.incrementarVersao(), 255);
		assertEquals(version.incrementarVersao(), 0);
	}
}