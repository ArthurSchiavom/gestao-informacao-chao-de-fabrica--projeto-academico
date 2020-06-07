package Mensagens.domain;

import ChaoDeFabrica.domain.Maquina.IdMaquina;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HelloBroadcast extends MensagemUDP {
	public HelloBroadcast(Version version, int port) throws UnknownHostException {
		super(version, Codigos.HELLO, new IdMaquina(0), new MessageData(new RawData("")),
				InetAddress.getByName("255.255.255.255"), port);
	}
}
