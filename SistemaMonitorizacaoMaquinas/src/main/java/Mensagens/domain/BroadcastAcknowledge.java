package Mensagens.domain;

import java.net.DatagramPacket;

public class BroadcastAcknowledge extends MensagemUDP {
	public  BroadcastAcknowledge(DatagramPacket packet) {
		super(packet);
	}

	public int getIdLinhaProducao() {
		MessageData mensagem = getMessageData();
		return Integer.valueOf(mensagem.rawData.getTextContent().trim());
	}
}
