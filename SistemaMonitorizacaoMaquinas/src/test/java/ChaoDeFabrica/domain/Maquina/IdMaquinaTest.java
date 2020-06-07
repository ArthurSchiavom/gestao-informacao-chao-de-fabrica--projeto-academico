package ChaoDeFabrica.domain.Maquina;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IdMaquinaTest {

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueIdMenorQue65535() {
		new IdMaquina(65536);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueIdMaiorQue0() {
		new IdMaquina(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueHa2Bytes() {
		byte[] bytes = new byte[3];
		new IdMaquina(bytes);
	}

	@Test
	public void leastSignificativeByte() {
		IdMaquina construtor1 = new IdMaquina(512);
		assertEquals(construtor1.leastSignificativeByte(), 0);
		byte[] bytes = {2, 3};
		IdMaquina construtor2 = new IdMaquina(bytes);
		assertEquals(construtor2.leastSignificativeByte(), 2);
	}

	@Test
	public void mostSignificativeByte() {
		IdMaquina construtor1 = new IdMaquina(256);
		assertEquals(construtor1.mostSignificativeByte(), 1);
		byte[] bytes = {2, 3};
		IdMaquina construtor2 = new IdMaquina(bytes);
		assertEquals(construtor2.mostSignificativeByte(), 3);
	}
}