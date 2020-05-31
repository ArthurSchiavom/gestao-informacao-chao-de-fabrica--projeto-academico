package Mensagens.domain;

import LinhaProducao.domain.Maquina.IdMaquina;
import org.junit.Test;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class HelloBroadcastTest {
	@Test(expected = IllegalArgumentException.class)
	public void garantirQueNadaVersaoNaoPodeSerNull() {
		try {
			new HelloBroadcast(null, 9999);
		} catch (UnknownHostException e) {
			fail();
		}
	}

	@Test
	public void getUDPPacket() {
		try {
			HelloBroadcast helloBroadcast = new HelloBroadcast(new Version(0), 9999);
			DatagramPacket packet = helloBroadcast.getUdpPacket();
			InetAddress address = InetAddress.getByName("255.255.255.255");
			assertEquals(packet.getAddress(), address);
		} catch (UnknownHostException e) {
			fail();
		}
	}

	@Test
	public void getVersion() {
		try {
			HelloBroadcast helloBroadcast = new HelloBroadcast(new Version(0), 9999);
			assertEquals(helloBroadcast.getVersion(), new Version(0));
		} catch (UnknownHostException e) {
			fail();
		}
	}

	@Test
	public void getCodigo() {
		try {
			HelloBroadcast helloBroadcast = new HelloBroadcast(new Version(0), 9999);
			assertEquals(helloBroadcast.getCodigo(), Codigos.HELLO);
		} catch (UnknownHostException e) {
			fail();
		}
	}

	@Test
	public void getIdMaquina() {
		try {
			HelloBroadcast helloBroadcast = new HelloBroadcast(new Version(0), 9999);
			assertEquals(helloBroadcast.getIdMaquina(), new IdMaquina(0));
		} catch (UnknownHostException e) {
			fail();
		}
	}

	@Test
	public void getMessageData() {
		try {
			HelloBroadcast helloBroadcast = new HelloBroadcast(new Version(0), 9999);
			assertEquals(helloBroadcast.getMessageData(), new MessageData(new RawData("")));
		} catch (UnknownHostException e) {
			fail();
		}
	}
}