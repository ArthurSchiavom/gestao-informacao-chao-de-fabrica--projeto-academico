package Mensagens.domain;

import LinhaProducao.domain.Maquina.IdMaquina;
import org.junit.Test;

import java.net.DatagramPacket;

import static org.junit.Assert.assertEquals;

public class BroadcastAcknowledgeTest {
	@Test(expected = IllegalArgumentException.class)
	public void garantirQueBroadcastNaoPodeTerUmPacoteNull() {
		new BroadcastAcknowledge(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void garantirQuePacoteTem6Bytes() {
		byte[] bytes = new byte[4];
		DatagramPacket data = new DatagramPacket(bytes, bytes.length);
		new BroadcastAcknowledge(data);
	}

	@Test
	public void getLinhaProducao() {
		byte[] string = "2   ".getBytes();
		byte[] bytes = {0,0,1,0,4,0,0,0,0,0};
		System.arraycopy(string, 0, bytes, 6, string.length);
		DatagramPacket data = new DatagramPacket(bytes, bytes.length);
		BroadcastAcknowledge broadcastAcknowledge = new BroadcastAcknowledge(data);
		assertEquals(broadcastAcknowledge.getIdLinhaProducao(), 2);
	}

	@Test
	public void getVersion() {
		byte[] string = "2   ".getBytes();
		byte[] bytes = {9,0,1,0,4,0,0,0,0,0};
		System.arraycopy(string, 0, bytes, 6, string.length);
		DatagramPacket data = new DatagramPacket(bytes, bytes.length);
		BroadcastAcknowledge broadcastAcknowledge = new BroadcastAcknowledge(data);
		assertEquals(broadcastAcknowledge.getVersion(), new Version(9));
	}

	@Test
	public void getCodigo() {
		byte[] string = "2   ".getBytes();
		byte[] bytes = {0,0,1,0,4,0,0,0,0,0};
		System.arraycopy(string, 0, bytes, 6, string.length);
		DatagramPacket data = new DatagramPacket(bytes, bytes.length);
		BroadcastAcknowledge broadcastAcknowledge = new BroadcastAcknowledge(data);
		assertEquals(broadcastAcknowledge.getCodigo(), Codigos.HELLO);
	}

	@Test
	public void getMessageData() {
		byte[] string = "2   ".getBytes();
		byte[] bytes = {0,0,1,0,4,0,0,0,0,0};
		System.arraycopy(string, 0, bytes, 6, string.length);
		DatagramPacket data = new DatagramPacket(bytes, bytes.length);
		BroadcastAcknowledge broadcastAcknowledge = new BroadcastAcknowledge(data);
		assertEquals(broadcastAcknowledge.getMessageData().length(), 4);
		assertEquals(broadcastAcknowledge.getMessageData().rawData.getTextContent(), "2   ");
	}

	@Test
	public void getIdMaquina() {
		byte[] string = "2   ".getBytes();
		byte[] bytes = {0,0,1,0,4,0,0,0,0,0};
		System.arraycopy(string, 0, bytes, 6, string.length);
		DatagramPacket data = new DatagramPacket(bytes, bytes.length);
		BroadcastAcknowledge broadcastAcknowledge = new BroadcastAcknowledge(data);
		assertEquals(broadcastAcknowledge.getIdMaquina(), new IdMaquina(1));
	}
}