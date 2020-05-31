package Mensagens.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataLengthTest {
	@Test(expected = IllegalArgumentException.class)
	public void garantirQueDataLengthMenorQue506() {
		new DataLength(507);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueDataLengthMaiorQue0() {
		new DataLength(-1);
	}

	@Test
	public void leastSignificativeByte() {
		DataLength construtor1 = new DataLength(256);
		assertEquals(construtor1.leastSignificativeByte(), 0);
		byte[] bytes = {2, 3};
		DataLength construtor2 = new DataLength(bytes);
		assertEquals(construtor2.leastSignificativeByte(), 2);
	}

	@Test
	public void mostSignificativeByte() {
		DataLength construtor1 = new DataLength(259);
		assertEquals(construtor1.mostSignificativeByte(), 1);
		byte[] bytes = {2, 3};
		DataLength construtor2 = new DataLength(bytes);
		assertEquals(construtor2.mostSignificativeByte(), 3);
	}

	@Test
	public void value() {
		byte[] bytes = {5, 0};
		DataLength construtor2 = new DataLength(bytes);
		assertEquals(construtor2.value(), 5);
	}
}