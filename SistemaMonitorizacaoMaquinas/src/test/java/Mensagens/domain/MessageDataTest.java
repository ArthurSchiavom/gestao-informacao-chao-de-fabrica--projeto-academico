package Mensagens.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageDataTest {
	@Test(expected = IllegalArgumentException.class)
	public void garantirQueMessageDataTemRawData() {
		new MessageData((RawData) null);
	}

	@Test
	public void garantirQueRawDataMantemValorCoerente() {
		byte[] rawBytes = {5,0,'t','e','s','t','e'};
		MessageData messageData = new MessageData(rawBytes);
		assertEquals(new String(messageData.rawData.toByteArray()), "teste");
	}

	@Test
	public void lengthDeMessageDataEquivaleALengthDeRawData() {
		RawData rawData = new RawData("teste");
		MessageData messageData = new MessageData(rawData);
		assertEquals(messageData.length(), rawData.byteArraySize());
	}
}