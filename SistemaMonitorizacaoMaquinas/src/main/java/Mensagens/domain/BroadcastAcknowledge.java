package Mensagens.domain;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class BroadcastAcknowledge extends MensagemUDP {
	public BroadcastAcknowledge(DatagramPacket packet) {
		super(packet);
	}

	public int getIdLinhaProducao() {
		MessageData mensagem = getMessageData();
//		System.out.println("Message Data: " + mensagem.rawData.getTextContent().trim());
		try {
			return Integer.valueOf(mensagem.rawData.getTextContent().trim());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public final InetAddress getIp() {
		return getUdpPacket().getAddress();
	}
}
