package Mensagens.domain;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class BroadcastAcknowledge extends MensagemUDP {
	public  BroadcastAcknowledge(DatagramPacket packet) {
		super(packet);
	}

	public int getIdLinhaProducao() {
		MessageData mensagem = getMessageData();
		return Integer.valueOf(mensagem.rawData.getTextContent().trim());
	}

	public final InetAddress getIp() {
		return getUdpPacket().getAddress();
	}
}
