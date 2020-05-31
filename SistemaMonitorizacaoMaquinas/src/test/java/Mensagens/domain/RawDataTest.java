package Mensagens.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class RawDataTest {
	@Test(expected = IllegalArgumentException.class)
	public void garantirQueRawDataNaoTemStringNull() {
		new RawData((String) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQueRawDataNaoTemStringNullBytes() {
		new RawData((byte[]) null);
	}

	@Test
	public void getTextContext() {
		RawData raw1 = new RawData("teste");
		assertEquals(raw1.getTextContent(), "teste");
		byte[] bytes = {'t','e','s','t','e','1'};
		RawData raw2 = new RawData(bytes);
		assertEquals(raw2.getTextContent(), "teste1");
	}

	@Test
	public void garantirQueLengthEstaCorreto() {
		RawData raw1 = new RawData("teste");
		assertEquals(raw1.byteArraySize(), 5);
		byte[] bytes = {'t','e','s','t','e','1'};
		RawData raw2 = new RawData(bytes);
		assertEquals(raw2.byteArraySize(), 6);
	}
}