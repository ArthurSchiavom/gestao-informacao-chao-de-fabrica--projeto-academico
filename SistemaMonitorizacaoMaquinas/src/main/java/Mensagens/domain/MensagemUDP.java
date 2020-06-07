package Mensagens.domain;

import ChaoDeFabrica.domain.Maquina.IdMaquina;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * Mensagem UDP que segue as regras do protocolo de informação
 * https://moodle.isep.ipp.pt/pluginfile.php/326053/mod_resource/content/1/LEI-2019-20-Sem4-_Projeto_ProtocoloComunicacao.pdf
 */
abstract class MensagemUDP{
	private DatagramPacket udpPacket;

	MensagemUDP(Version version, Codigos codigo, IdMaquina idMaquina, MessageData data, InetAddress address, int port) {
		if(version == null || codigo == null || idMaquina == null || data == null || address == null) {
			throw new IllegalArgumentException("nenhum valor pode ser null");
		}
		byte[] dados = new byte[6 + data.length()];
		dados[0] = version.byteValue();
		dados[1] = codigo.value();
		dados[2] = idMaquina.leastSignificativeByte();
		dados[3] = idMaquina.mostSignificativeByte();
		dados[4] = data.length.leastSignificativeByte();
		dados[5] = data.length.mostSignificativeByte();
		System.arraycopy(data.rawData.toByteArray(), 0, dados, 6, data.length());
		udpPacket = new DatagramPacket(dados, dados.length, address, port);
	}

	MensagemUDP(DatagramPacket packet) {
		if(packet == null) {
			throw new IllegalArgumentException("Packet não pode ser null");
		}
		if(packet.getData().length < 6) {
			throw new IllegalArgumentException("Data retornada nunca pode ser menor que 6");
		}
		this.udpPacket = packet;
	}

	public final DatagramPacket getUdpPacket() {
		return udpPacket;
	}

	public final Version getVersion() {
		return new Version(Byte.toUnsignedInt(udpPacket.getData()[0]));
	}

	public final Codigos getCodigo() {
		return Codigos.searchForCodigo(Byte.toUnsignedInt(udpPacket.getData()[1]));
	}

	final MessageData getMessageData() {
		return new MessageData(Arrays.copyOfRange(udpPacket.getData(), 4, udpPacket.getLength()));
	}

	public final IdMaquina getIdMaquina() {
		return new IdMaquina(Arrays.copyOfRange(udpPacket.getData(), 2, 4));
	}
}
